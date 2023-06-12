package chistousov.ilya.common

data class AlertDialogConfig(
    val title: String,
    val message: String,
    val positiveButtonText: String,
    val positiveButtonCallback: () -> Unit,
    val negativeButtonText: String? = null,
)
