package at.thomasgorke.android.composeexample.utils


fun String.toSnakeCase(): String = this.replaceFirstChar { it.uppercase() }