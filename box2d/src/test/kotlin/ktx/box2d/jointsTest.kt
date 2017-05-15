package ktx.box2d

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef
import org.junit.Assert.*
import org.junit.Test

/**
 * Tests joint builder methods.
 */
class JointsTest {
  @Test
  fun `should create Joint with a custom JointDef`() {
    val (bodyA, bodyB) = getBodies()
    val jointDefinition = DistanceJointDef()

    val joint = bodyA.jointWith(bodyB, jointDefinition) {
      length = 2f
      assertSame(jointDefinition, this)
    }

    assertSame(bodyA, joint.bodyA)
    assertSame(bodyB, joint.bodyB)
    assertEquals(bodyA.position, joint.anchorA)
    assertEquals(bodyB.position, joint.anchorB)
    assertTrue(joint is DistanceJoint)
    assertEquals(2f, (joint as DistanceJoint).length)
    bodyA.world.dispose()
  }

  @Test
  fun `should create RevoluteJoint`() {
    val (bodyA, bodyB) = getBodies()

    val joint = bodyA.revoluteJointWith(bodyB) {
      motorSpeed = 2f
    }

    assertSame(bodyA, joint.bodyA)
    assertSame(bodyB, joint.bodyB)
    assertEquals(bodyA.position, joint.anchorA)
    assertEquals(bodyB.position, joint.anchorB)
    assertEquals(2f, joint.motorSpeed)
    bodyA.world.dispose()
  }

  @Test
  fun `should create PrismaticJoint`() {
    val (bodyA, bodyB) = getBodies()

    val joint = bodyA.prismaticJointWith(bodyB) {
      motorSpeed = 2f
    }

    assertSame(bodyA, joint.bodyA)
    assertSame(bodyB, joint.bodyB)
    assertEquals(bodyA.position, joint.anchorA)
    assertEquals(bodyB.position, joint.anchorB)
    assertEquals(2f, joint.motorSpeed)
    bodyA.world.dispose()
  }

  @Test
  fun `should create DistanceJoint`() {
    val (bodyA, bodyB) = getBodies()

    val joint = bodyA.distanceJointWith(bodyB) {
      length = 2f
    }

    assertSame(bodyA, joint.bodyA)
    assertSame(bodyB, joint.bodyB)
    assertEquals(bodyA.position, joint.anchorA)
    assertEquals(bodyB.position, joint.anchorB)
    assertEquals(2f, joint.length)
    bodyA.world.dispose()
  }

  @Test
  fun `should create PulleyJoint`() {
    val (bodyA, bodyB) = getBodies()

    val joint = bodyA.pulleyJointWith(bodyB) {
      ratio = 2f
    }

    assertSame(bodyA, joint.bodyA)
    assertSame(bodyB, joint.bodyB)
    assertEquals(bodyA.position, joint.anchorA)
    assertEquals(bodyB.position, joint.anchorB)
    assertEquals(2f, joint.ratio)
    bodyA.world.dispose()
  }

  @Test
  fun `should create MouseJoint`() {
    val (bodyA, bodyB) = getBodies()

    val joint = bodyA.mouseJointWith(bodyB) {
      dampingRatio = 0.2f
    }

    assertSame(bodyA, joint.bodyA)
    assertSame(bodyB, joint.bodyB)
    // Anchors are not checked, as initial joint's anchor positions do not match bodies' positions.
    assertEquals(0.2f, joint.dampingRatio)
    bodyA.world.dispose()
  }

  @Test
  fun `should create GearJoint`() {
    val (bodyA, bodyB) = getBodies()
    val jointA = bodyB.revoluteJointWith(bodyA) {}
    val jointB = bodyA.revoluteJointWith(bodyB) {}

    val joint = bodyA.gearJointWith(bodyB) {
      joint1 = jointA
      joint2 = jointB
    }

    assertSame(bodyA, joint.bodyA)
    assertSame(bodyB, joint.bodyB)
    assertEquals(bodyA.position, joint.anchorA)
    assertEquals(bodyB.position, joint.anchorB)
    assertSame(jointA, joint.joint1)
    assertSame(jointB, joint.joint2)
    bodyA.world.dispose()
  }

  @Test
  fun `should create WheelJoint`() {
    val (bodyA, bodyB) = getBodies()

    val joint = bodyA.wheelJointWith(bodyB) {
      motorSpeed = 2f
    }

    assertSame(bodyA, joint.bodyA)
    assertSame(bodyB, joint.bodyB)
    assertEquals(bodyA.position, joint.anchorA)
    assertEquals(bodyB.position, joint.anchorB)
    assertEquals(2f, joint.motorSpeed)
    bodyA.world.dispose()
  }

  @Test
  fun `should create WeldJoint`() {
    val (bodyA, bodyB) = getBodies()

    val joint = bodyA.weldJointWith(bodyB) {
      dampingRatio = 0.2f
    }

    assertSame(bodyA, joint.bodyA)
    assertSame(bodyB, joint.bodyB)
    assertEquals(bodyA.position, joint.anchorA)
    assertEquals(bodyB.position, joint.anchorB)
    assertEquals(0.2f, joint.dampingRatio)
    bodyA.world.dispose()
  }

  @Test
  fun `should create FrictionJoint`() {
    val (bodyA, bodyB) = getBodies()

    val joint = bodyA.frictionJointWith(bodyB) {
      maxForce = 2f
    }

    assertSame(bodyA, joint.bodyA)
    assertSame(bodyB, joint.bodyB)
    assertEquals(bodyA.position, joint.anchorA)
    assertEquals(bodyB.position, joint.anchorB)
    assertEquals(2f, joint.maxForce)
    bodyA.world.dispose()
  }

  @Test
  fun `should create RopeJoint`() {
    val (bodyA, bodyB) = getBodies()

    val joint = bodyA.ropeJointWith(bodyB) {
      maxLength = 2f
    }

    assertSame(bodyA, joint.bodyA)
    assertSame(bodyB, joint.bodyB)
    assertEquals(bodyA.position, joint.anchorA)
    assertEquals(bodyB.position, joint.anchorB)
    assertEquals(2f, joint.maxLength)
    bodyA.world.dispose()
  }

  @Test
  fun `should create MotorJoint`() {
    val (bodyA, bodyB) = getBodies()

    val joint = bodyA.motorJointWith(bodyB) {
      maxForce = 2f
    }

    assertSame(bodyA, joint.bodyA)
    assertSame(bodyB, joint.bodyB)
    assertEquals(bodyA.position, joint.anchorA)
    assertEquals(bodyB.position, joint.anchorB)
    assertEquals(2f, joint.maxForce)
    bodyA.world.dispose()
  }

  private fun getBodies(): Pair<Body, Body> {
    val world = createWorld()
    val bodyA = world.body {
      position.set(-1f, 0f)
      box(1f, 1f) {}
    }
    val bodyB = world.body {
      position.set(1f, 0f)
      box(1f, 1f) {}
    }
    return bodyA to bodyB
  }
}
