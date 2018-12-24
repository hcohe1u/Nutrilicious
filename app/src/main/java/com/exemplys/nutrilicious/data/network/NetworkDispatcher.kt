package com.exemplys.nutrilicious.data.network

import kotlinx.coroutines.newFixedThreadPoolContext

val NETWORK = newFixedThreadPoolContext(2, "NETWORK")  // Dedicated network context

