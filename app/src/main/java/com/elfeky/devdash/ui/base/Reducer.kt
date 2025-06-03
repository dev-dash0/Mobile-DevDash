package com.elfeky.devdash.ui.base


interface Reducer<S : Reducer.ViewState, E : Reducer.ViewEvent, F : Reducer.ViewEffect> {
    fun reduce(previousState: S, event: E): Pair<S, F?>

    interface ViewState
    interface ViewEvent
    interface ViewEffect
}