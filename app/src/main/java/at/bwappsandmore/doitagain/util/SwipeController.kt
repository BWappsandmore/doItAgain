package at.bwappsandmore.doitagain.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import at.bwappsandmore.doitagain.R.drawable
import at.bwappsandmore.doitagain.enums.ButtonState


class SwipeController(_context: Context) : Callback() {

    private var swipeBack = false

    private var buttonShowedState: ButtonState = ButtonState.GONE

    private val buttonWidth: Float = 200.0F

    private var buttonsActions: SwipeControllerActions? = null

    private var buttonInstance: RectF? = null

    private var currentItemViewHolder: RecyclerView.ViewHolder? = null

    private val context = _context

    constructor(_context: Context, _buttonsAction : SwipeControllerActions) : this(_context){
        this.buttonsActions = _buttonsAction
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return makeMovementFlags(0, RIGHT)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        if (swipeBack) {
            swipeBack = buttonShowedState != ButtonState.GONE
            return 0
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        var mdX = dX
        if (actionState == ACTION_STATE_SWIPE) {
           if (buttonShowedState != ButtonState.GONE) {
                if (buttonShowedState == ButtonState.LEFT_VISIBLE)
                    mdX = Math.max(dX, buttonWidth)
                if (buttonShowedState == ButtonState.RIGHT_VISIBLE)
                    mdX = Math.min(dX, -buttonWidth)
                super.onChildDraw(c, recyclerView, viewHolder, mdX, dY, actionState, isCurrentlyActive)
            } else {
                setTouchListener(c, recyclerView, viewHolder, mdX, dY, actionState, isCurrentlyActive)
            }
        }

        if (buttonShowedState == ButtonState.GONE) {
            super.onChildDraw(c, recyclerView, viewHolder, mdX, dY, actionState, isCurrentlyActive)
        }
        currentItemViewHolder = viewHolder
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListener(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

        recyclerView.setOnTouchListener { _, event ->
            swipeBack =
                event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP
            if (swipeBack) {
                if (dX < -buttonWidth)
                    buttonShowedState = ButtonState.RIGHT_VISIBLE
                else if (dX > buttonWidth)
                    buttonShowedState = ButtonState.LEFT_VISIBLE

                if (buttonShowedState != ButtonState.GONE) {
                    setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    setItemsClickable(recyclerView, false)
                }
            }
            return@setOnTouchListener false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchDownListener(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        recyclerView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN)
                setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return@setOnTouchListener false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchUpListener(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        recyclerView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                super@SwipeController.onChildDraw(c, recyclerView, viewHolder, 0F, dY, actionState, false)
                recyclerView.setOnTouchListener { _, _ ->
                    false
                }

                setItemsClickable(recyclerView, true)
                swipeBack = false

                if (buttonsActions != null && buttonInstance != null && buttonInstance!!.contains(event.x, event.y)) {
                    if (buttonShowedState == ButtonState.LEFT_VISIBLE){
                        buttonsActions!!.onLeftClicked(viewHolder.adapterPosition)}
                    else if (buttonShowedState == ButtonState.RIGHT_VISIBLE)
                        buttonsActions!!.onRightClicked(viewHolder.adapterPosition)

                }
                buttonShowedState = ButtonState.GONE
                currentItemViewHolder = null
            }
            return@setOnTouchListener false
        }
    }

    private fun setItemsClickable(recyclerView: RecyclerView, isClickable: Boolean) {
        recyclerView.let {
            for (i in 0 until it.childCount) {
                it.getChildAt(i).isClickable = isClickable
            }
        }
    }

    private fun drawButtons(c: Canvas, viewHolder: RecyclerView.ViewHolder) {
        val buttonWidthWithoutPadding = buttonWidth - 20
        val corners = 16.0F

        val itemView = viewHolder.itemView
        val p = Paint()

        val leftButton = RectF(itemView.left.toFloat(), itemView.top.toFloat(), itemView.left + buttonWidthWithoutPadding, itemView.bottom.toFloat())
        p.color = Color.parseColor("#ff0099cc")
        c.drawRoundRect(leftButton, corners, corners, p)
        val leftIcon: Drawable? = ContextCompat.getDrawable(context, drawable.reset_white_24px)
        val leftIconBitmap = drawableToBitmap(leftIcon!!)
        c.drawBitmap(leftIconBitmap, leftButton.left + (leftButton.width()/2) - (leftIconBitmap.width.div(2)) , leftButton.top + (leftButton.height()/2)-(leftIconBitmap.height.div(2)),p)

        val rightButton = RectF(itemView.right - buttonWidthWithoutPadding, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
        p.color = Color.parseColor("#ff0099cc")
        c.drawRoundRect(rightButton, corners, corners, p)
        val rightIcon: Drawable? = ContextCompat.getDrawable(context, drawable.add_alert_white_24px)
        val rightIconBitmap = drawableToBitmap(rightIcon!!)
        c.drawBitmap(rightIconBitmap,rightButton.left + (rightButton.width()/2) - (rightIconBitmap.width.div(2)), rightButton.top + (rightButton.height()/2)- (rightIconBitmap.height.div(2)),p)

        buttonInstance = null

        if (buttonShowedState == ButtonState.LEFT_VISIBLE) {
            buttonInstance = leftButton
        } else if (buttonShowedState == ButtonState.RIGHT_VISIBLE) {
            buttonInstance = rightButton
        }
    }

    fun onDraw(c: Canvas) {
        currentItemViewHolder?.let { drawButtons(c, it) }
    }

    private fun drawableToBitmap(d: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(d.intrinsicWidth, d.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val c = Canvas(bitmap)
        d.apply {
            setBounds(0,0,c.width,c.height)
            draw(c)
        }

        return bitmap
    }
}