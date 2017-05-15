package ktx.box2d

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Joint
import com.badlogic.gdx.physics.box2d.JointDef
import com.badlogic.gdx.physics.box2d.joints.*

/**
 * Allows to create a [Joint] with custom [JointDef] instance. `this` [Body] will be set as the [JointDef.bodyA].
 * @param body will be set as the [JointDef.bodyB].
 * @param jointDefinition can be customized with [init] block.
 * @param init inlined. [JointDef.bodyA], [JointDef.bodyB] and [JointDef.type] must not be changed. Invoked after joint
 *    definition default values are preset.
 * @return a fully constructed [Joint] based on the customized [jointDefinition].
 * @see gearJointWith
 * @see ropeJointWith
 * @see weldJointWith
 * @see motorJointWith
 * @see mouseJointWith
 * @see wheelJointWith
 * @see pulleyJointWith
 * @see distanceJointWith
 * @see frictionJointWith
 * @see revoluteJointWith
 * @see prismaticJointWith
 */
inline fun <JointDefinition : JointDef> Body.jointWith(
    body: Body,
    jointDefinition: JointDefinition,
    init: (@Box2DDsl JointDefinition).() -> Unit): Joint {
  jointDefinition.bodyA = this
  jointDefinition.bodyB = body
  jointDefinition.init()
  return world.createJoint(jointDefinition)
}

/**
 * Allows to create a [RevoluteJoint]. `this` [Body] will be set as the [JointDef.bodyA] and will be available
 * through [Joint.getBodyA].
 * @param body will be set as [JointDef.bodyB] and available through [Joint.getBodyB].
 * @param init inlined. [JointDef.bodyA], [JointDef.bodyB] and [JointDef.type] must not be changed. Invoked after joint
 *    definition default values are preset.
 * @return a new instance of [RevoluteJoint] created with the customized [RevoluteJointDef].
 * @see RevoluteJointDef
 * @see RevoluteJoint
 */
inline fun Body.revoluteJointWith(body: Body, init: (@Box2DDsl RevoluteJointDef).() -> Unit): RevoluteJoint
    = jointWith(body, RevoluteJointDef(), init) as RevoluteJoint

/**
 * Allows to create a [PrismaticJoint]. `this` [Body] will be set as the [JointDef.bodyA] and will be available
 * through [Joint.getBodyA].
 * @param body will be set as [JointDef.bodyB] and available through [Joint.getBodyB].
 * @param init inlined. [JointDef.bodyA], [JointDef.bodyB] and [JointDef.type] must not be changed. Invoked after joint
 *    definition default values are preset.
 * @return a new instance of [PrismaticJoint] created with the customized [PrismaticJointDef].
 * @see PrismaticJointDef
 * @see PrismaticJoint
 */
inline fun Body.prismaticJointWith(body: Body, init: (@Box2DDsl PrismaticJointDef).() -> Unit): PrismaticJoint
    = jointWith(body, PrismaticJointDef(), init) as PrismaticJoint

/**
 * Allows to create a [DistanceJoint]. `this` [Body] will be set as the [JointDef.bodyA] and will be available
 * through [Joint.getBodyA].
 * @param body will be set as [JointDef.bodyB] and available through [Joint.getBodyB].
 * @param init inlined. [JointDef.bodyA], [JointDef.bodyB] and [JointDef.type] must not be changed. Invoked after joint
 *    definition default values are preset.
 * @return a new instance of [DistanceJoint] created with the customized [DistanceJointDef].
 * @see DistanceJointDef
 * @see DistanceJoint
 */
inline fun Body.distanceJointWith(body: Body, init: (@Box2DDsl DistanceJointDef).() -> Unit): DistanceJoint
    = jointWith(body, DistanceJointDef(), init) as DistanceJoint

/**
 * Allows to create a [PulleyJoint]. `this` [Body] will be set as the [JointDef.bodyA] and will be available
 * through [Joint.getBodyA].
 * @param body will be set as [JointDef.bodyB] and available through [Joint.getBodyB].
 * @param init inlined. [JointDef.bodyA], [JointDef.bodyB] and [JointDef.type] must not be changed. Invoked after joint
 *    definition default values are preset.
 * @return a new instance of [PulleyJoint] created with the customized [PulleyJointDef].
 * @see PulleyJointDef
 * @see PulleyJoint
 */
inline fun Body.pulleyJointWith(body: Body, init: (@Box2DDsl PulleyJointDef).() -> Unit): PulleyJoint
    = jointWith(body, PulleyJointDef().apply {
  localAnchorA.set(0f, 0f)
  localAnchorB.set(0f, 0f)
}, init) as PulleyJoint

/**
 * Allows to create a [MouseJoint]. `this` [Body] will be set as the [JointDef.bodyA] and will be available
 * through [Joint.getBodyA].
 * @param body will be set as [JointDef.bodyB] and available through [Joint.getBodyB].
 * @param init inlined. [JointDef.bodyA], [JointDef.bodyB] and [JointDef.type] must not be changed. Invoked after joint
 *    definition default values are preset.
 * @return a new instance of [MouseJoint] created with the customized [MouseJointDef].
 * @see MouseJointDef
 * @see MouseJoint
 */
inline fun Body.mouseJointWith(body: Body, init: (@Box2DDsl MouseJointDef).() -> Unit): MouseJoint
    = jointWith(body, MouseJointDef(), init) as MouseJoint

/**
 * Allows to create a [GearJoint]. `this` [Body] will be set as the [JointDef.bodyA] and will be available
 * through [Joint.getBodyA].
 * @param body will be set as [JointDef.bodyB] and available through [Joint.getBodyB].
 * @param init inlined. [JointDef.bodyA], [JointDef.bodyB] and [JointDef.type] must not be changed. Invoked after joint
 *    definition default values are preset.
 * @return a new instance of [GearJoint] created with the customized [GearJointDef].
 * @see GearJointDef
 * @see GearJoint
 */
inline fun Body.gearJointWith(body: Body, init: (@Box2DDsl GearJointDef).() -> Unit): GearJoint
    = jointWith(body, GearJointDef(), init) as GearJoint

/**
 * Allows to create a [WheelJoint]. `this` [Body] will be set as the [JointDef.bodyA] and will be available
 * through [Joint.getBodyA].
 * @param body will be set as [JointDef.bodyB] and available through [Joint.getBodyB].
 * @param init inlined. [JointDef.bodyA], [JointDef.bodyB] and [JointDef.type] must not be changed. Invoked after joint
 *    definition default values are preset.
 * @return a new instance of [WheelJoint] created with the customized [WheelJointDef].
 * @see WheelJointDef
 * @see WheelJoint
 */
inline fun Body.wheelJointWith(body: Body, init: (@Box2DDsl WheelJointDef).() -> Unit): WheelJoint
    = jointWith(body, WheelJointDef(), init) as WheelJoint

/**
 * Allows to create a [WeldJoint]. `this` [Body] will be set as the [JointDef.bodyA] and will be available
 * through [Joint.getBodyA].
 * @param body will be set as [JointDef.bodyB] and available through [Joint.getBodyB].
 * @param init inlined. [JointDef.bodyA], [JointDef.bodyB] and [JointDef.type] must not be changed. Invoked after joint
 *    definition default values are preset.
 * @return a new instance of [WeldJoint] created with the customized [WeldJointDef].
 * @see WeldJointDef
 * @see WeldJoint
 */
inline fun Body.weldJointWith(body: Body, init: (@Box2DDsl WeldJointDef).() -> Unit): WeldJoint
    = jointWith(body, WeldJointDef(), init) as WeldJoint

/**
 * Allows to create a [FrictionJoint]. `this` [Body] will be set as the [JointDef.bodyA] and will be available
 * through [Joint.getBodyA].
 * @param body will be set as [JointDef.bodyB] and available through [Joint.getBodyB].
 * @param init inlined. [JointDef.bodyA], [JointDef.bodyB] and [JointDef.type] must not be changed. Invoked after joint
 *    definition default values are preset.
 * @return a new instance of [FrictionJoint] created with the customized [FrictionJointDef].
 * @see FrictionJointDef
 * @see FrictionJoint
 */
inline fun Body.frictionJointWith(body: Body, init: (@Box2DDsl FrictionJointDef).() -> Unit): FrictionJoint
    = jointWith(body, FrictionJointDef(), init) as FrictionJoint

/**
 * Allows to create a [RopeJoint]. `this` [Body] will be set as the [JointDef.bodyA] and will be available
 * through [Joint.getBodyA].
 * @param body will be set as [JointDef.bodyB] and available through [Joint.getBodyB].
 * @param init inlined. [JointDef.bodyA], [JointDef.bodyB] and [JointDef.type] must not be changed. Invoked after joint
 *    definition default values are preset.
 * @return a new instance of [RopeJoint] created with the customized [RopeJointDef].
 * @see RopeJointDef
 * @see RopeJoint
 */
inline fun Body.ropeJointWith(body: Body, init: (@Box2DDsl RopeJointDef).() -> Unit): RopeJoint
    = jointWith(body, RopeJointDef().apply {
  localAnchorA.set(0f, 0f)
  localAnchorB.set(0f, 0f)
}, init) as RopeJoint

/**
 * Allows to create a [MotorJoint]. `this` [Body] will be set as the [JointDef.bodyA] and will be available
 * through [Joint.getBodyA].
 * @param body will be set as [JointDef.bodyB] and available through [Joint.getBodyB].
 * @param init inlined. [JointDef.bodyA], [JointDef.bodyB] and [JointDef.type] must not be changed. Invoked after joint
 *    definition default values are preset.
 * @return a new instance of [MotorJoint] created with the customized [MotorJointDef].
 * @see MotorJointDef
 * @see MotorJoint
 */
inline fun Body.motorJointWith(body: Body, init: (@Box2DDsl MotorJointDef).() -> Unit): MotorJoint
    = jointWith(body, MotorJointDef(), init) as MotorJoint
