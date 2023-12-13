import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux'

export const LogFilterComponent = () => {

    const projects = useSelector((state) => state.home.projects)
    const tasks = useSelector((state) => state.home.tasks)
    const subtasks = useSelector((state) => state.home.subtasks)

    const [filteredTask, setFilteredTasks] = useState(useSelector((state) => state.home.tasks));
    const [filteredSubtask, setFilteredSubtasks] = useState(useSelector((state) => state.home.tasks));

    
    const [ layout, setLayout ] = useState({
        projectDisabled: false,
        taskDisabled: true,
        subtaskDisabled: true
    });

    const [ filter, setFilter ] = useState({
        project: 0,
        task: 0,
        subtask: 0
    });

    const onSelectProject = (e) => {
        if(e.target.value !== "0") {
            setLayout({...setLayout, taskDisabled: false, subtaskDisabled: true})
        } else {
            setLayout({...layout, taskDisabled: true, subtaskDisabled: true})
        }
        document.getElementById('taskFilter').selectedIndex = 0
        document.getElementById('subtaskFilter').selectedIndex = 0
        setFilter({project: e.target.value, task: "0", subtask: "0"})
        const filtered = tasks.filter((item) => item.project_id.toString() === e.target.value);
        setFilteredTasks(filtered)
    }

    const onSelectTask = (e) => {
        if(e.target.value !== "0") {
            setLayout({...layout, subtaskDisabled: false})
        } else {
            setLayout({
                ...layout,
                subtaskDisabled: true
            })
        }
        document.getElementById('subtaskFilter').selectedIndex = 0
        setFilter({...filter, task: e.target.value, subtask: "0"})
        const filtered = subtasks.filter((item) => item.task_id.toString() == e.target.value)
        setFilteredSubtasks(filtered);
    }

    const onSelectSubtask = (e) => {
        setFilter({...filter, subtask: e.target.value})
    }

    return (
        <div>
            <p>Project:</p>
            <select id='projectTask' disabled={layout.projectDisabled} onChange={onSelectProject}>
                <option key={0} value={0}>{" "}</option>
                {
                    projects.map((item, index) => (
                        <option key={item.id} value={item.id}>{item.name}</option>
                    ))
                }
            </select>
            <span>{filter.project}</span>
            <p>Task:</p>
            <select id='taskFilter' disabled={layout.taskDisabled} onChange={onSelectTask}>
                <option key={0} value={0}>{" "}</option>
                {
                    filteredTask.map((item, index) => (
                        <option key={item.id} value={item.id}>{item.name}</option>
                    ))
                }
            </select>
            <span>{filter.task}</span>
            <p>Subtask:</p>
            <select id='subtaskFilter' disabled={layout.subtaskDisabled} onChange={onSelectSubtask}>
                <option key={0} value={0}>{" "}</option>
                {
                    filteredSubtask.map((item, index) => (
                        <option key={item.id} value={item.id}>{item.name}</option>
                    ))
                }
            </select>
            <span>{filter.subtask}</span>
        </div>
    )

    
}