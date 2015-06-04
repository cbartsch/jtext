JSText: JavaScript text adventure game

by Christian Bartsch (s1410629001) and Stefan Niederhumer (s1410629014)

Usage:
Start local HTTP server (e.g. with "ripple emulate"), open index.html.
Play game by entering text commands in the input field and pressing enter.

Or use with gulp by installing node package ("npm install") and running with "npm run gulp dev".

Work separation:
Stefan: UI, prediction service, gulp file, angular config
Christian: Game logic (game.js)

Technologies:
- Angular, angular-sanitize, angular-scroll-glue
- Bootstrap
- jQuery
- Gulp, browser-sync, sourcemaps, uglify, concat
- NPM
- JSON