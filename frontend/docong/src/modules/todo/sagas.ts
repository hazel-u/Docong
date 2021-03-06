import { call, put, takeEvery } from 'redux-saga/effects'
import {
  changeTodoActivate,
  deleteTodo,
  findTodo,
  findUserTodos,
  modifyTodo,
  modifyTodoStatus,
  saveTodo,
  Todos,
} from '../../api/todo'
import {
  changeTodoActivateAsync,
  CHANGE_TODO_ACTIVATE,
  deleteTodoAsync,
  DELETE_TODO,
  findTodoAsync,
  findUserTodosAsync,
  FIND_TODO,
  FIND_USER_TODOS,
  modifyTodoAsync,
  modifyTodoStatusAsync,
  MODIFY_TODO,
  MODIFY_TODO_STATUS,
  saveTodoAsync,
  SAVE_TODO,
} from './actions'

function* saveTodoSaga(action: ReturnType<typeof saveTodoAsync.request>) {
  try {
    yield call(saveTodo, action.payload)
    yield put(findUserTodosAsync.request(null))
  } catch (e: any) {
    console.error(e)
  }
}

function* findTodoSaga(action: ReturnType<typeof findTodoAsync.request>) {
  try {
    yield call(findTodo, action.payload)
  } catch (e: any) {
    console.error(e)
  }
}

function* modifyTodoSaga(action: ReturnType<typeof modifyTodoAsync.request>) {
  try {
    yield call(modifyTodo, action.payload.todoId, action.payload.todoInput)
    yield put(findUserTodosAsync.request(null))
  } catch (e: any) {
    console.error(e)
  }
}

function* deleteTodoSaga(action: ReturnType<typeof deleteTodoAsync.request>) {
  try {
    yield call(deleteTodo, action.payload)
    yield put(findUserTodosAsync.request(null))
  } catch (e: any) {
    console.error(e)
  }
}

function* modifyTodoStatusSaga(
  action: ReturnType<typeof modifyTodoStatusAsync.request>
) {
  try {
    yield call(
      modifyTodoStatus,
      action.payload.todoId,
      action.payload.todoStatus
    )
    yield put(findUserTodosAsync.request(null))
  } catch (e: any) {
    console.error(e)
  }
}

function* findUserTodosSaga(
  action: ReturnType<typeof findUserTodosAsync.request>
) {
  try {
    const userTodos: Todos = yield call(findUserTodos)
    yield put(findUserTodosAsync.success(userTodos))
  } catch (e: any) {
    yield put(findUserTodosAsync.failure(e))
    console.error(e)
  }
}

function* changeTodoActivateSaga(
  action: ReturnType<typeof changeTodoActivateAsync.request>
) {
  try {
    yield call(
      changeTodoActivate,
      action.payload.todoId,
      action.payload.activateData
    )
  } catch (e: any) {
    console.error(e)
  }
}

export function* todoSaga() {
  yield takeEvery(SAVE_TODO, saveTodoSaga)
  yield takeEvery(FIND_TODO, findTodoSaga)
  yield takeEvery(MODIFY_TODO, modifyTodoSaga)
  yield takeEvery(DELETE_TODO, deleteTodoSaga)
  yield takeEvery(MODIFY_TODO_STATUS, modifyTodoStatusSaga)
  yield takeEvery(FIND_USER_TODOS, findUserTodosSaga)
  yield takeEvery(CHANGE_TODO_ACTIVATE, changeTodoActivateSaga)
}
