package com.rasyidcode.busschedule.viewmodels

import androidx.lifecycle.ViewModel
import com.rasyidcode.busschedule.database.schedule.Schedule
import com.rasyidcode.busschedule.database.schedule.ScheduleDao
import kotlinx.coroutines.flow.Flow

class BusScheduleViewModel(
    private val scheduleDao: ScheduleDao
) : ViewModel() {

    fun fullSchedule(): Flow<List<Schedule>> = scheduleDao.getAll()

    fun scheduleForStopName(name: String): Flow<List<Schedule>>  = scheduleDao.getByStopName(name)

}