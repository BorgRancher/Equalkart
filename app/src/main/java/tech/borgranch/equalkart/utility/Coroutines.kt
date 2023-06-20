package tech.borgranch.equalkart.utility

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

val commonPool = Dispatchers.Main
val uiDispatchers = Dispatchers.Main.immediate
val ioPool = Dispatchers.IO
val defaultPool = Dispatchers.Default
val unconfinedPool = Dispatchers.Unconfined

val commonScope = CoroutineScope(commonPool)
val uiScope = CoroutineScope(uiDispatchers)
val ioScope = CoroutineScope(ioPool)
val defaultScope = CoroutineScope(defaultPool)
val unconfinedScope = CoroutineScope(unconfinedPool)
