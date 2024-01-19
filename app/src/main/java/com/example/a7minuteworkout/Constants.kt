package com.example.a7minuteworkout

object Constants {
    fun defaultExersiceList(): ArrayList<ExersiceModel>{
        var exersiceList = ArrayList<ExersiceModel>()

        var jumpingJacks = ExersiceModel(1, "Jumping Jacks", R.drawable.ic_jumping_jacks, false, false)
        exersiceList.add(jumpingJacks)

        var abdominalCrunch = ExersiceModel(2, "Abdominal Crunch", R.drawable.ic_abdominal_crunch, false, false)
        exersiceList.add(abdominalCrunch)

        var highKneesRunningInPlace = ExersiceModel(3, "High Knees Running In Place", R.drawable.ic_high_knees_running_in_place, false, false)
        exersiceList.add(highKneesRunningInPlace)

        var lunge = ExersiceModel(4, "Lunge", R.drawable.ic_lunge, false, false)
        exersiceList.add(lunge)

        var plank = ExersiceModel(5, "Planck", R.drawable.ic_plank, false, false)
        exersiceList.add(plank)

        var pushUp = ExersiceModel(6, "Push Up", R.drawable.ic_push_up, false, false)
        exersiceList.add(pushUp)

        var pushUpAndRotation = ExersiceModel(7, "Push Up And Rotation", R.drawable.ic_push_up_and_rotation, false, false)
        exersiceList.add(pushUpAndRotation)

        var sidePlank = ExersiceModel(8, "Side Plank", R.drawable.ic_side_plank, false, false)
        exersiceList.add(sidePlank)

        var squat = ExersiceModel(9, "Squat", R.drawable.ic_squat, false, false)
        exersiceList.add(squat)

        var stepUpOntoChair = ExersiceModel(10, "Step Up On To Chair", R.drawable.ic_step_up_onto_chair, false, false)
        exersiceList.add(stepUpOntoChair)

        var tricepsDipOnChair = ExersiceModel(11, "Triceps Dip On Chair", R.drawable.ic_triceps_dip_on_chair, false, false)
        exersiceList.add(tricepsDipOnChair)

        var wallSit = ExersiceModel(12, "Wall Sit", R.drawable.ic_wall_sit, false, false)
        exersiceList.add(wallSit)

        return exersiceList
    }
}