const gulp = require('gulp'),
	lint = require('gulp-jshint'),
	plumber = require('gulp-plumber'),
	rename = require('gulp-rename'),
	uglify = require('gulp-uglify'),
	util = require('gulp-util');


gulp.task('default', lintAndMinify);

/**
 * Lint and Minifies code.
 */
function lintAndMinify() {
	gulp
		.src('./src/ng-cpf-cnpj.js')
		.pipe(plumber({
			errorHandler: errorHandler
		}))
		.pipe(lint())
		.pipe(lint.reporter('default'))
		.pipe(gulp.dest('./dist/'))
		.pipe(rename('ng-cpf-cnpj.min.js'))
		.pipe(uglify())
		.pipe(gulp.dest('./dist/'));
}

/**
 * Utility function to report errors in the cmd
 *
 * @param      {error}  err     The error
 */
function errorHandler(err) {
	util.beep();
	util.log(util.colors.red('! ! ! ! ! ! ! ! ! !  Error ! ! ! ! ! ! ! ! ! ! '));
	util.log(util.colors.blue(err));
	util.log(util.colors.red('! ! ! ! ! ! ! ! ! ! !  End ! ! ! ! ! ! ! ! ! ! !'));
	this.emit('end');
}