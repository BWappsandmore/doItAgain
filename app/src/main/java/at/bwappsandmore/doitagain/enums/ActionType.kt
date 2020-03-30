package at.bwappsandmore.doitagain.enums

enum class ActionType(private val value: Int) {

    UNDEFINE(-1),
    REMIND(0),
    EDIT(1),
    SHARE(2),
    DELETE(3),
    RESET_COUNTER(4)
}