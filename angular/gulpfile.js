"use strict";

var gulp = require("gulp");
var include = require("gulp-include");
var del = require("del");
var browserSync = require("browser-sync");

gulp.task("default", [], function() {
    return gulp.start("build");
});

gulp.task("clean", function(cb) {
    del(["js"], {force: true}, cb);
});

gulp.task("build", ["clean", "scripts"]);

gulp.task("scripts", function() {
    return gulp.src("modules/jstext.js").
        pipe(include()).
        pipe(gulp.dest("js"));
});

gulp.task("server", function(){
    browserSync({
        server: {
            baseDir: '.'
        }
    });
});

gulp.task("dev", ["default"], function() {
    gulp.watch(["modules/**/*"], ["scripts", browserSync.reload]);
    gulp.watch(["css/**/*"], browserSync.reload);
    gulp.watch(["index.html", "view/**/*.html"], browserSync.reload);
    gulp.start("server");
});