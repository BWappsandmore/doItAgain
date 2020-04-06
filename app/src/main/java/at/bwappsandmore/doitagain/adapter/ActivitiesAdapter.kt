package at.bwappsandmore.doitagain.adapter

import android.graphics.Color
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
            }
            itemView.add_alertIB.setOnClickListener {
                onActionClicked(activities[adapterPosition], ActionType.REMIND)
                toggleIcon(it)
            }
            containerView.apply {

                setOnClickListener {
                    onActionClicked(activities[adapterPosition], ActionType.EDIT)
                }

                setOnLongClickListener {
                    onItemLongClicked(activities[adapterPosition], ActionType.DELETE)
                    select(it)
                    return@setOnLongClickListener true
                }
            }
        }

        fun bind(vo: DoItAgainEntity) {
            itemView.activity_title.text = vo.name
            itemView.sinceDaysTV.text = Days.daysBetween(
                vo.dateActivity.toLocalDate(),
                DateTime.now().toLocalDate()
            ).days.toString()
            if (vo.hasReminderSet) itemView.add_alertIB.setImageResource(R.drawable.add_alert_red_24px)
            else itemView.add_alertIB.setImageResource(R.drawable.add_alert_24px)
        }

        private fun select(v: View) {
            v.apply {
                setBackgroundColor(Color.parseColor("#ff0099cc"))
                activity_title.setTextColor(Color.WHITE)
                sinceTV.setTextColor(Color.WHITE)
                sinceDaysTV.setTextColor(Color.WHITE)
                daysTV.setTextColor(Color.WHITE)
                resetIB.setBackgroundColor(Color.parseColor("#ff0099cc"))
                resetIB.setImageResource(R.drawable.reset_white_24px)
                add_alertIB.setBackgroundColor(Color.parseColor("#ff0099cc"))
                add_alertIB.setImageResource(R.drawable.add_alert_white_24px)
            }
        }

        private fun toggleIcon(v: View) {
            if (v.add_alertIB.id == R.drawable.add_alert_24px)
                v.add_alertIB.setImageResource(R.drawable.add_alert_red_24px)
            else v.add_alertIB.setImageResource(R.drawable.add_alert_24px)
        }
    }
}

