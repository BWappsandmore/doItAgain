package at.bwappsandmore.doitagain.enums

enum class ActionType(private val value: Int) {

    UNDEFINE(-1),
    REMIND(0),
    EDIT(1),
    FindByActivity(2),
    FindById(3),
    DELETE(4),
    RESET_COUNTER(5);

    fun parse(value: Int): ActionType {
        if (value == -1)
            return UNDEFINE
        val items = values()
        for (item in items) {
            if (item.value == value) return item
        }
        return UNDEFINE
    }


}