package ktx.inject

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.GdxRuntimeException
import com.nhaarman.mockito_kotlin.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import java.util.Random

/**
 * Tests the KTX dependency injection module: [Context] implementation.
 */
class DependencyInjectionTest {
  val context = Context()

  @Test
  fun `should register components`() {
    context.bind { listOf<Any>() }

    assertTrue(List::class.java in context)
  }

  @Test
  fun `should inject components`() {
    context.bind { mutableListOf<Any>() }

    val list = context.inject<MutableList<String>>()

    assertTrue(MutableList::class.java in context)
    assertNotNull(list)
    list.add("Test")
    assertEquals(1, list.size)
    assertEquals("Test", list[0])
  }

  @Test
  fun `should bind singletons`() {
    val singleton = Random()

    context.bindSingleton(singleton)

    assertTrue(context.contains<Random>())
    val provided = context.inject<Random>()
    assertSame(singleton, provided)
    assertSame(context.inject<Random>(), context.inject<Random>())

    val provider = context.provider<Random>()
    assertSame(singleton, provider())
    assertSame(provider(), provider())
  }

  @Test
  fun `should bind singletons to multiple types`() {
    val singleton = java.lang.String("Singleton")

    context.bindSingleton(singleton, String::class.java, CharSequence::class.java)

    assertTrue(context.contains<String>())
    assertTrue(context.contains<CharSequence>())
    assertSame(singleton, context.inject<String>())
    assertSame(context.inject<String>(), context.provider<CharSequence>()())
    assertNotSame("Singleton", context.inject<String>())
  }

  @Test
  fun `should remove singletons`() {
    val singleton = Random()
    context.bindSingleton(singleton)

    val singletonProvider = context.remove<Random>()

    assertFalse(context.contains<Random>())
    assertNotNull(singletonProvider)
    assertSame(singleton, singletonProvider?.invoke())
  }

  @Test
  fun `should remove providers`() {
    val provider = { Random() }
    context.bind(provider)

    val removed = context.remove<Random>()

    assertFalse(context.contains<Random>())
    assertNotNull(removed)
    assertSame(provider, removed)
  }

  @Test
  fun `should not throw an exception when trying to remove absent provider`() {
    val removed = context.remove<Random>()

    assertNull(removed)
  }

  @Test
  fun `should inject providers`() {
    context.bind { Random() }

    val provider = context.provider<Random>()

    assertTrue(context.contains<Random>())
    assertNotNull(provider)
    val random1 = provider()
    val random2 = provider()
    assertNotNull(random1)
    assertNotNull(random2)
    assertNotSame(random1, random2)
  }

  @Test
  fun `should bind providers to multiple types`() {
    context.bind(String::class.java, CharSequence::class.java) { java.lang.String("New") }

    assertTrue(context.contains<String>())
    assertTrue(context.contains<CharSequence>())
    assertNotSame(context.inject<String>(), context.provider<CharSequence>()())
  }

  @Test(expected = InjectionException::class)
  fun `should throw exception when trying to register provider for same type multiple times`() {
    context.bind { "Test." }
    context.bind { "Should throw." }
  }

  @Test(expected = InjectionException::class)
  fun `should throw exception when trying to register singleton of the same type multiple times`() {
    context.bindSingleton("Test.")
    context.bindSingleton("Should throw.")
  }

  @Test(expected = InjectionException::class)
  fun `should throw exception when trying to register provider and singleton of same type`() {
    context.bindSingleton("Test.")
    context.bind { "Should throw." }
  }

  @Test(expected = InjectionException::class)
  fun `should throw exception when trying to register provider for same types`() {
    context.bind(String::class.java, String::class.java) { "Should throw." }
  }

  @Test(expected = InjectionException::class)
  fun `should throw exception when trying to register singleton for same types`() {
    context.bindSingleton("Should throw.", String::class.java, String::class.java)
  }

  @Test
  fun `should inject context`() {
    val injected = context.inject<Context>()

    assertNotNull(injected)
    assertTrue(Context::class.java in context)
    assertTrue(context.contains<Context>())
    assertSame(context, injected)
  }

  @Test
  fun `should remove providers on clear except for Context provider`() {
    context.bind { "Test" }

    context.clear()

    assertTrue(context.contains<Context>())
    assertFalse(context.contains<String>())
  }

  @Test
  fun `should fill context with builder DSL`() {
    context.register {
      bind { Random() }
      bindSingleton("Test")
    }

    assertTrue(context.contains<Random>())
    assertTrue(context.contains<String>())
  }

  @Test(expected = InjectionException::class)
  fun `should throw exception upon injection of missing type`() {
    context.inject<String>()
  }

  @Test(expected = InjectionException::class)
  fun `should throw exception upon injection of provider of missing type`() {
    context.provider<String>()
  }

  @Test
  fun `should inject instances with invocation syntax`() {
    context.bind { Random() }

    val injected: Random = context()

    assertNotNull(injected)
  }

  @Test
  fun `should remove providers on dispose except for Context provider`() {
    context.bind { "Test" }

    context.dispose()

    assertTrue(context.contains<Context>())
    assertFalse(context.contains<String>())
  }

  @Test
  fun `should dispose of Disposable components`() {
    val nonDisposable = Any()
    context.bindSingleton(nonDisposable)
    val singleton = mock<Disposable>()
    context.bindSingleton(singleton)
    val provider = mock<DisposableProvider<Random>>()
    context.bind(provider)

    context.dispose()

    assertFalse(context.contains<Any>())
    assertFalse(context.contains<Disposable>())
    verify(singleton).dispose()
    assertFalse(context.contains<Random>())
    verify(provider).dispose()
  }

  @Test
  fun `should dispose of Disposable components with error handling`() {
    Gdx.app = mock<Application>()
    val singleton = mock<Disposable> {
      on(it.dispose()) doThrow GdxRuntimeException("Expected.")
    }
    context.bindSingleton(singleton)
    val provider = mock<DisposableProvider<Random>>()
    context.bind(provider)

    context.dispose()

    assertFalse(context.contains<Disposable>())
    verify(singleton).dispose()
    verify(Gdx.app).error(eq("KTX"), any(), argThat { this is GdxRuntimeException }) // Ensures exception was logged.
    assertFalse(context.contains<Random>())
    verify(provider).dispose()
  }

  @After
  fun `clear context`() {
    context.clear()
  }

  /** Implements both [Disposable] and provider interfaces. */
  interface DisposableProvider<out Type> : Disposable, () -> Type
}

/**
 * Tests specialized [SingletonProvider] that allows to dispose of singletons registered in a [Context].
 */
class SingletonProviderTest {
  @Test
  fun `should not throw exception when trying to dispose singleton that does not implement Disposable`() {
    val singletonProvider = SingletonProvider(Random())

    singletonProvider.dispose()
  }

  @Test
  fun `should dispose of Disposable singleton`() {
    val singleton = mock<Disposable>()
    val singletonProvider = SingletonProvider(singleton)

    singletonProvider.dispose()

    verify(singleton).dispose()
  }

  @Test(expected = GdxRuntimeException::class)
  fun `should rethrow Disposable exceptions`() {
    val singleton = mock<Disposable> {
      on(it.dispose()) doThrow GdxRuntimeException("Expected.")
    }
    val singletonProvider = SingletonProvider(singleton)

    singletonProvider.dispose()
  }
}
