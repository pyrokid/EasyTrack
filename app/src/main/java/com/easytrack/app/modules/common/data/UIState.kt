package com.easytrack.app.modules.common.data

open class UIState

object EmptyUIState : UIState()

class LoadingUIState(val style: LoadingStyle) : UIState()

class ErrorUIState(val throwable: Throwable) : UIState()

class RetryUIState(val throwable: Throwable, val retry: () -> Unit) : UIState()