package tech.borgranch.equalkart.domain.usecases

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import tech.borgranch.equalkart.utility.ioPool
import tech.borgranch.equalkart.utility.uiDispatchers
import kotlin.coroutines.CoroutineContext

abstract class UseCase<out T, in Params> {
    abstract suspend fun run(params: Params): T

    operator fun invoke(params: Params, onResult: (T) -> Unit = {}) {
        val job = async(ioPool) { run(params) }

        launch(uiDispatchers) { onResult(job.await()) }
    }

    private fun async(
        context: CoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> T,
    ): Deferred<T> {
        return CoroutineScope(context).async(context, start, block)
    }

    private fun launch(
        context: CoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit,
    ): Job {
        return CoroutineScope(context).launch(context, start, block)
    }
}
