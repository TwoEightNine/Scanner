package global.msnthrp.scanner.base

/**
 * helps to handle one-time events with LiveData
 *
 * when ui component resubscribes to live data, it receives a value stored there
 * in case of one-time event ui shouldn't receive the event as soon as it is one-time
 *
 * this event returns null if its value was requested earlier
 */
class OneTimeEvent<T>(private val value: T) {

    private var handled = false

    /**
     * returns value and marks it as handled
     */
    fun getValue(): T? = if (!handled) {
        handled = true
        value
    } else {
        null
    }

    /**
     * returns value anyway
     */
    fun getValueWithoutHandling() = value

    fun handle() {
        handled = true
    }
}