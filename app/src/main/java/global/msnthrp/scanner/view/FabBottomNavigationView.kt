package global.msnthrp.scanner.view

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import global.msnthrp.scanner.R

class FabBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr) {

    init {

        var fabSize = 100f
        var fabCradleMargin = 16f
        var fabCradleCornerRadius = 16f
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.FabBottomNavigationView,
            0, 0
        ).apply {

            try {
//                fabSize = getDimensionPixelSize(R.styleable.FabBottomNavigationView_fabSize, 100)
//                    .toFloat()
//                fabCradleMargin = getDimensionPixelSize(R.styleable.FabBottomNavigationView_fabCradleMargin, 16)
//                    .toFloat()
//                fabCradleCornerRadius =
//                    getDimensionPixelSize(R.styleable.FabBottomNavigationView_fabCradleCornerRadius, 16)
//                        .toFloat()
            } finally {
                recycle()
            }
        }

        //There will be magic
        val topCurvedEdgeTreatment =
            TopCurvedEdgeTreatment(fabCradleMargin, fabCradleCornerRadius, 0f).apply {
                fabDiameter = fabSize
            }
//        val shapePathModel = ShapePathModel().apply {
//            topEdge = topCurvedEdgeTreatment
//        }
        val shapeAppearanceModel = ShapeAppearanceModel.Builder()
            .setTopEdge(topCurvedEdgeTreatment)
            .build()
        val materialShapeDrawable = MaterialShapeDrawable(shapeAppearanceModel).apply {
            setTint(ContextCompat.getColor(context, R.color.accent))
            shadowElevation = 4
            shadowRadius = 16
            isShadowEnabled = true
            paintStyle = Paint.Style.FILL_AND_STROKE
        }
        background = materialShapeDrawable
    }
}