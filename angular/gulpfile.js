var gulp = require("gulp");
var include = require('gulp-include');

gulp.task("default", [], function() {
    return gulp.start("build");
});

gulp.task("build", ["scripts"]);

gulp.task("scripts", function() {
    return gulp.src("modules/jstext.js").
        pipe(include()).
        pipe(gulp.dest("js"));
});