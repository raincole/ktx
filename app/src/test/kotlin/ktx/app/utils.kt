package ktx.app

import com.badlogic.gdx.Graphics
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock

/**
 * @param delta time since last render.
 * @return [Graphics] mock that always returns the passed value as delta time.
 */
fun mockGraphicsWithDeltaTime(delta: Float): Graphics = mock {
  on(it.deltaTime) doReturn delta
  on(it.rawDeltaTime) doReturn delta
}
