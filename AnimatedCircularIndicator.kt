@Composable
fun AnimatedCircularIndicator(
    value: Float,
    minValue: Float = 0f,
    maxValue: Float = 100f,

    modifier: Modifier = Modifier,
    strokeWidth: Float = 24f,
    backgroundStrokeWidth: Float = 16f,

    backgroundColor: Color = Color.LightGray,

    valueColor: (Float) -> Color,

    animationDuration: Int = 900,

    centerContent: @Composable (Float) -> Unit
) {

    val normalized =
        ((value - minValue) / (maxValue - minValue))
            .coerceIn(0f, 1f)

    val targetAngle = normalized * 360f

    val animatedSweep by animateFloatAsState(
        targetValue = targetAngle,
        animationSpec = tween(animationDuration),
        label = "CircularIndicator"
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {

            drawArc(
                color = backgroundColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(
                    backgroundStrokeWidth,
                    cap = StrokeCap.Round
                )
            )

            drawArc(
                color = valueColor(value),
                startAngle = -90f,
                sweepAngle = animatedSweep,
                useCenter = false,
                style = Stroke(
                    strokeWidth,
                    cap = StrokeCap.Round
                )
            )
        }

        centerContent(value)
    }
}
