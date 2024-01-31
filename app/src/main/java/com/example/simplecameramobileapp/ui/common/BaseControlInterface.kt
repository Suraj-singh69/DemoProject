package com.example.simplecameramobileapp.ui.common

/**
 * Base Controller Functions used in activities, fragments, dialogs, bottomsheet
 */
interface BaseControlInterface {

    /**
     * All initialization related work will be done in this method.
     * i.e. Handling lifecycle methods.
     */
    abstract fun onInitialized()

    /**
     * All observer listener code will be handled in this method inside controllers.
     */
    open fun addObservers() {}

    /**
     * All click action code will be handled in this method inside controllers.
     */
    abstract fun setUpClicks()

}