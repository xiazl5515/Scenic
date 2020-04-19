import scenic.simulators.carla.actions as actions


behavior TeleportForwardBehavior():
	''' Repeatedly teleports actor forward in direction of it heading '''

	while True:
		take actions.OffsetAction(0.5)


behavior AccelerateThenBrakeBehavior(accelTime, throttleInc, brakeTime, brakeInc):
	''' Increase actor's throttle by <throttle> over <accelTime> seconds before increasing actor's brakes by <brake> over <brakeTime> seconds
		<accelTime>   : positive integer
		<throttleInc> : float in range [0.0, 1.0]
		<brakeTime>   : postiive integer
		<brakeInc>    : float in range [0.0, 1.0] '''

	throttleIncrement = throttleInc / accelTime
	brakeIncrement = brakeInc / brakeTime

	do actions.IncreaseThrottleAction(throttleIncrement) for accelTime seconds
	take actions.SetThrottleAction(0.0)  # in preparation to brake
	do actions.IncreaseBrakeAction(brakeIncrement) for brakeTime seconds


behavior LanekeepingBehavior():
	while True:
		take actions.AlignSteerToLaneAction()


behavior LaneChangeLeftBehavior():
	pass


behavior LaneChangeRightBehavior():
	pass


behavior PassingBehavior():
	# precondition: there exists a left or right lane
	try:
		LaneChangeRightBehavior()
	interrupt when ... :
		LaneChangeLeftBehavior()
	#AccelerateBehavior()
