/**
 * Created by Chrisu on 26.05.2015.
 */

var WITH_SEPARATOR = " with ";

function getOrDefault(value, defaultValue) {
    return typeof value !== "undefined" ? value : defaultValue;
}

var actions = {
    enable: function (gameState, action, entity) {
        entity.enabled = getOrDefault(action.value, !entity.enabled);
    },
    remove: function (gameState, action, entity) {
        entity.remove();
    },
    state: function (gameState, action, entity) {
        entity.state = action.value;
    },
    take: function (gameState, action, entity) {
        entity.remove();
        gameState.inventory.add(entity);
    },
    visible: function (gameState, action, entity) {
        entity.visible = getOrDefault(action.value, !entity.visible);
    },
    win: function (gameState, action, entity) {
        gameState.win();
    }
};

var conditions = {
    state: function (gameState, model, entity) {
        return entity.state === model.value;
    },
    item: function (gameState, model, entity) {
        return entity.name in gameState.inventory.objects;
    }
};

function EntityCommand(gameState, cmd, cmdKey, param) {
    var withIndex = param.indexOf(WITH_SEPARATOR);
    var entityName, withItemName;
    if (withIndex >= 0) {
        entityName = param.substr(withIndex + WITH_SEPARATOR.length);
        withItemName = param.substr(0, withIndex);
    } else {
        entityName = param;
        withItemName = null;
    }

    var current = gameState.location;
    var entity = entityName.length == 0
        ? current : current.objects[entityName];

    if (withItemName && !(withItemName in gameState.inventory.objects)) {
        gameState.print(withItemName + " not in inventory.");
    } else if (!entity || !entity.visible) {
        gameState.print("Can not " + cmd + ": " + entityName + " not found");
    } else if (!entity.enabled) {
        gameState.print("Not possible right now.")
    } else {
        var action = entity[withItemName ? cmdKey + "_with" : cmdKey];

        if (!action) {
            gameState.print("Not possible.");
        } else if (withItemName && withItemName !== action.item) {
            gameState.print("Can not use " + withItemName + " with this.");
        } else {
            var conditionMet = true;
            if (action.if) {
                action.if.forEach(function (condition) {
                    var conditionFunc = conditions[condition.type];
                    if (conditionFunc) {
                        var targets = condition.targets || [entity.name];
                        conditionMet = targets.every(function (targetId) {
                            var entity = gameState.allEntities[targetId];
                            var result = conditionFunc(gameState, condition, entity);
                            if (!result) {
                                gameState.print(condition.else);
                            }
                            return result;
                        });
                    }
                });
            }
            if (conditionMet) {
                if (action.text) {
                    gameState.print(action.text);
                }
                if (action.do) {
                    action.do.forEach(function (doAction) {
                        var actionFunc = actions[doAction.type];
                        if (actionFunc) {
                            var targets = doAction.targets || [entity.name];
                            console.log("targets", targets, "type", doAction.type);
                            targets.forEach(function (targetId) {
                                var entity = gameState.allEntities[targetId];
                                actionFunc(gameState, doAction, entity);
                            });
                        }
                    });
                }
            }
        }
    }
}

function GoCommand(gameState, cmd, cmdKey, param) {
    var locationName = param;
    if (!locationName) {
        gameState.print("Where to go to?");
    } else {
        var location = gameState.allEntities[locationName];
        if (location && location.visible) {
            if (location === gameState.location) {
                gameState.print("You are already at " + locationName);
            } else if (location.enabled && gameState.location.adjacent.indexOf(location.name) >= 0) {
                gameState.location = location;
                gameState.print("You are now at: " + location.name);
            } else {
                gameState.print("Can not go to " + location.name);
            }
        } else {
            gameState.print(locationName + " not found. Can not go.");
        }
    }
}

function HelpCommand(gameState) {
    gameState.print("Available commands: " + interactions.map(function (interaction) {
            return interaction.cmds[0];
        }).join(", ") + ".");
}

function Interaction(cmds, cmdKey, command) {
    this.cmds = cmds;
    this.do = function (gameState, cmd, param) {
        command(gameState, cmd, cmdKey, param);
    };
}

var interactions = [
    new Interaction(["use"], "use", EntityCommand),
    new Interaction(["take", "pick up"], "take", EntityCommand),
    new Interaction(["look at", "look", "inspect"], "look", EntityCommand),
    new Interaction(["go to", "walk to", "go", "walk", "cd"], "go", GoCommand),
    new Interaction(["help", "?"], "", HelpCommand)
];

interactions.forCmd = function (cmd) {
    var usedCmd = "";
    var matches = $.grep(interactions, function (interaction) {
        return interaction.cmds.some(function (iCmd) {
            if (cmd.substr(0, iCmd.length) === iCmd &&
                (cmd.length == iCmd.length || cmd[iCmd.length] == " ")) {
                usedCmd = iCmd;
                return true;
            }
            return false;
        });
    });
    var interaction = matches.length > 0 ? matches[0] : null;
    var param = cmd.substr(usedCmd.length).trim();
    return {
        do: function (gameState) {
            if (interaction) {
                interaction.do(gameState, usedCmd, param);
            }
            return this;
        },
        else: function (callback) {
            if (!interaction) {
                callback();
            }
        }
    }
};

function Entity(name, model, parent) {
    this.name = name;
    this.parent = parent;

    this.text = model.text;
    this.imageUrl = model.imageUrl;

    this.visible = getOrDefault(model.visible, true);
    this.enabled = getOrDefault(model.enabled, true);

    this.state = model.state || "";

    this.use = model.use || null;
    this.use_with = model.use_with || null;
    this.take = model.take || null;
    this.look = model.look || null;

    this.objects = model.objects || {};

    //for locations
    this.adjacent = model.adjacent || [];

    this.remove = function () {
        if (this.parent) {
            if (this.name in this.parent.objects) {
                console.log("removing");
                delete this.parent.objects[this.name];
            } else {
                console.log("remove(), but ", this.name, "not found in", this.parent.name);
            }
        } else {
            console.log("remove(), but no parent for", this.name)
        }
    };

    this.add = function (entity) {
        this.objects[entity.name] = entity;
        entity.parent = this;
    }
}

function buildEntityMap(model) {
    var allEntities = {};
    for (var key in model.locations) {
        var loc = model.locations[key];
        var locEntity = new Entity(key, loc, null);
        allEntities[key] = locEntity;

        for (var objKey in loc.objects) {
            allEntities[objKey] = new Entity(objKey, loc.objects[objKey], locEntity);
        }
    }
    Object.keys(allEntities).forEach(function (name) {
        var entity = allEntities[name];
        var objectModel = entity.objects;
        entity.objects = {};
        Object.keys(objectModel).forEach(function (objName) {
            entity.objects[objName] = allEntities[objName];
        });
    });
    return allEntities;
}

function GameState(game, printer, winCallback, predictionService) {
    var gameState = this;

    this.game = game;
    this.print = printer;
    this.predictionService = predictionService;

    this.allEntities = buildEntityMap(game.model);

    this.location = this.allEntities[game.startLocationName];

    this.inventory = new Entity("inventory", {}, null);

    this.print(game.startText);

    this.win = function () {
        this.print("YOU WIN THE GAME");
        winCallback();
    };

    this.commandList = [];
    for (var i in interactions) {
        var interaction = interactions[i];
        for (var j in interaction.cmds) {
            gameState.commandList.push(interaction.cmds[j]);
        }
    }
    this.enter = function (text) {
        var trimmedText = text.trim();
        interactions.forCmd(trimmedText)
            .do(this)
            .else(function () {
                var prediction = predictionService.findClosestElement(trimmedText, gameState.commandList);
                if (prediction) {
                    gameState.print("Did you mean " + prediction + "?");
                } else {
                    gameState.print("I don't understand '" + trimmedText + "'.");
                }
            });
    }
}

function Game(model, predictionService) {
    this.startLocationName = model.start;
    this.startText = model.start_text;
    this.model = model;

    this.start = function (printer, winCallback, predictionService) {
        return new GameState(this, printer, winCallback, predictionService);
    }
}
