package at.bwappsandmore.doitagain.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import at.bwappsandmore.doitagain.enums.ActionType
import at.bwappsandmore.doitagain.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import at.bwappsandmore.doitagain.room.DoItAgainEntity
import org.joda.time.DateTime
import org.joda.time.Days

class ActivitiesAdapter(
    val onActionClicked: (DoItAgainEntity, ActionType) -> Unit,
    val onItemLongClicked: (DoItAgainEntity, ActionType) -> Unit
) : RecyclerView.Adapter<ActivitiesAdapter.ActivitiesViewHolder>() {

    private var activities = emptyList<DoItAgainEntity>()

    internal fun setActivities(activities: List<DoItAgainEntity>) {
        this.activities = activities
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivitiesViewHolder =
        ActivitiesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recyclerview_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ActivitiesViewHolder, position: Int) {
        holder.bind(activities[position])
    }

    override fun getItemCount() = activities.size

    inner class ActivitiesViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        // you can set an integer for each action
        init {
            itemView.resetIB.setOnClickListener {
                onActionClicked(activities[adapterPosition], ActionType.RESET_COUNTER)
                Log.d(null, "btn reset pressed")
            }
            itemView.editIB.setOnClickListener {
                onActionClicked(activities[adapterPosition], ActionType.EDIT)
                Log.d(null, "btn edit pressed")
            }
            containerView.apply {

                setOnLongClickListener{
                    onItemLongClicked(activities[adapterPosition],ActionType.DELETE)
/*                    setBackgroundColor(Color.parseColor("#ff0099cc"))
                    activity_title.setTextColor(Color.WHITE)
                    sinceDaysTV.setTextColor(Color.WHITE)
                    daysTV.setTextColor(Color.WHITE)
                    resetIB.setBackgroundColor(Color.parseColor("#ff0099cc"))
                    resetIB.setImageResource(R.drawable.reset_white_24px)
                    editIB.setBackgroundColor(Color.parseColor("#ff0099cc"))
                    editIB.setImageResource(R.drawable.edit_white_24px)
                    add_alertIB.setBackgroundColor(Color.parseColor("#ff0099cc"))
                    add_alertIB.setImageResource(R.drawable.add_alert_white_24px)
                    share_activityIB.setBackgroundColor(Color.parseColor("#ff0099cc"))
                    share_activityIB.setImageResource(R.drawable.ic_share_white_24dp)
                    onItemLongClicked.invoke(activities[adapterPosition], ActionType.DELETE)*/
                    return@setOnLongClickListener true
                }
            }
        }

        fun bind(vo: DoItAgainEntity) {
            itemView.activity_title.text = vo.name
            itemView.sinceDaysTV.text = Days.daysBetween(vo.dateActivity.toLocalDate(), DateTime.now().toLocalDate()).days.toString()
        }
    }
}

