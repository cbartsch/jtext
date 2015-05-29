/**
 * Created by Chrisu on 26.05.2015.
 */

var WITH_SEPARATOR = " with ";

function getOrDefault(value, defaultValue) {
    return typeof value !== "undefined" ? value : defaultValue;
}

var actions = {
    enabled: function (gameState, action, entity) {
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

function EntityInteraction(cmd) {
    this.cmd = cmd;
    this.do = function (gameState, param) {

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
            gameState.print(entityName + " not found");
        } else {
            var action = entity[withItemName ? this.cmd + "_with" : this.cmd];

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
                        action.do.forEach(function (action) {
                            var actionFunc = actions[action.type];
                            if (actionFunc) {
                                var targets = action.targets || [entity.name];
                                targets.forEach(function (targetId) {
                                    var entity = gameState.allEntities[targetId];
                                    actionFunc(gameState, action, entity);
                                });
                            }
                        });
                    }
                }
            }
        }
    }
}

function GoInteraction(cmd) {
    this.cmd = cmd;
    this.do = function (gameState, param) {
        var location = gameState.allEntities[param];
        if (location && location.visible) {
            if (gameState.location.adjacent.indexOf(location.name) >= 0) {
                gameState.location = location;
                gameState.print("You are now at: " + location.name);
            } else {
                gameState.print("Can not go to " + location.name);
            }
        } else {
            gameState.print(param + " not found. Can not go.");
        }
    };
}

var interactions = {
    look: new EntityInteraction("look"),
    take: new EntityInteraction("take"),
    use: new EntityInteraction("use"),
    go: new GoInteraction("go")
};

function Entity(name, model, parent) {
    this.name = name;
    this.parent = parent;
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
        Object.keys(entity.objects).forEach(function (objName) {
            entity.objects[objName] = allEntities[objName];
        });
    });
    return allEntities;
}

function GameState(game, printer, winCallback) {
    this.game = game;
    this.print = printer;

    this.allEntities = buildEntityMap(game.model);

    this.location = this.allEntities[game.startLocationName];

    this.inventory = new Entity("inventory", {}, null);

    this.print(game.startText);

    this.win = function () {
        this.print("YOU WIN THE GAME");
        winCallback();
    };

    this.enter = function (text) {
        var spacePos = text.indexOf(" ");
        var cmd, param;
        if (spacePos > 0) {
            cmd = text.substr(0, spacePos);
            param = text.substr(spacePos + 1);
        } else {
            cmd = text;
            param = "";
        }

        var interaction = interactions[cmd];
        if (interaction) {
            interaction.do(this, param);
        } else {
            this.print("What");
        }
    }
}

function Game(model) {
    this.startLocationName = model.start;
    this.startText = model.start_text;
    this.model = model;

    this.start = function (printer, winCallback) {
        return new GameState(this, printer, winCallback);
    }
}
