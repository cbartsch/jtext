/**
 * Created by Chrisu on 26.05.2015.
 */

var actions = {
    visible: function (gameState, action) {
        action.targets.forEach(function (targetId) {
            var entity = gameState.game.allEntities[targetId].model;
            var value = typeof action.value !== "undefined"
                ? action.value
                : !entity.visible;
            console.log("set visible of", entity, "to", value)
            entity.visible = value;
        });
    },
    state: function (gameState, action) {

    }
};

function EntityInteraction(cmd) {
    this.cmd = cmd;
    this.do = function (gameState, param) {
        var current = gameState.location;
        var entity = param.length == 0
            ? current.model
            : current.model.objects[param];

        console.log(entity)
        if (!entity || entity.visible === false) {
            gameState.print(param + " not found");
        } else {
            var action = entity[this.cmd];

            if (!action) {
                gameState.print("Not possible.");
            } else {
                if (action.text) {
                    gameState.print(action.text);
                }
                if (action.do) {
                    action.do.forEach(function (action) {
                        var actionFunc = actions[action.type];
                        if (actionFunc) {
                            actionFunc(gameState, action);
                        }
                    });
                }
            }
        }
    }
}

var interactions = {
    look: new EntityInteraction("look"),
    take: new EntityInteraction("take"),
    use: new EntityInteraction("use")
};

function Entity(name, model) {
    this.name = name;
    this.model = model;
}

function GameState(game, printer) {
    this.game = game;
    this.print = printer;

    this.location = game.allEntities[game.model.start];

    this.print(game.model.start_text);

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
    this.model = model;

    this.allEntities = {};
    for (var key in model.locations) {
        var loc = model.locations[key];
        this.allEntities[key] = new Entity(key, loc);

        for (var objKey in loc.objects) {
            this.allEntities[objKey] = new Entity(objKey, loc.objects[objKey]);
        }
    }

    this.start = function (printer) {
        return new GameState(this, printer);
    }
}
