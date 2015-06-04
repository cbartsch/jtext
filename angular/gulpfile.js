"use strict";

var gulp = require("gulp");
var include = require("gulp-include");
var del = require("del");
var browserSync = require("browser-sync");
var sourcemaps = require('gulp-sourcemaps');
var uglify = require('gulp-uglify');
var concat = require('gulp-concat');

gulp.task("default", [], function () {
    return gulp.start("build");
});

gulp.task("clean", function (cb) {
    del(["js/*"], {force: true}, cb);
});

gulp.task("build", ["clean", "scripts", "ext-scripts"]);

gulp.task("ext-scripts", function () {
    return gulp.src([
        "node_modules/jquery/dist/jquery.js",
        "node_modules/bootstrap/dist/js/bootstrap.js",
        "node_modules/angular/angular.js",
        "node_modules/angularjs-scroll-glue/src/scrollglue.js",
        "node_modules/angular-sanitize/angular-sanitize.js"
    ]).pipe(concat("jstext-ext.js"))
        .pipe(uglify({mangle: false}))
        .pipe(gulp.dest("js"));
});

gulp.task("scripts", function () {
    return gulp.src([
        "modules/*.js"
    ]).pipe(sourcemaps.init())
        .pipe(include())
        .pipe(concat("jstext.js"))
        .pipe(uglify({mangle: false}))
        .pipe(sourcemaps.write())
        .pipe(gulp.dest("js"));
});

gulp.task("server", function () {
    browserSync({
        server: {
            baseDir: '.'
        }
    });
});

gulp.task("dev", ["default"], function () {
    gulp.watch(["modules/**/*"], ["scripts", browserSync.reload]);
    gulp.watch(["css/**/*"], browserSync.reload);
    gulp.watch(["index.html", "view/**/*.html"], browserSync.reload);
    gulp.start("server");
});