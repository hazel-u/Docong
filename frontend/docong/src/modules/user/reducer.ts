import { createReducer } from 'typesafe-actions'
import { UserAction, UserState } from './types'
import {
  CHANGE_USER_TIMER_STATUS,
  CHANGE_USER_TIMER_TIME,
  CHANGE_USER_TIMER_TYPE,
} from './actions'
// import {
//   asyncState,
//   createAsyncReducer,
//   transformToArray,
// } from '../../lib/reducerUtils'

// --- recerUtils 의 asyncState 를 활용한 리팩토링 ---
const initialState: UserState = {
  // 개인 타이머 상태
  userTimer: {
    selectedType: { name: 'Normal', time: 1500 },
    status: 'stop',
    time: 1500,
  },
  // userTimer: asyncState.initial(),
}

// --- reducerUtils 의 asyncState, createAsyncReducer, transformToArray 를 활용한 리팩토링 ---
// const user = createReducer<UserState, UserAction>(initialState).handleAction(
//   changeUserTimer,
//   (state, action) => ({ ...state, [action.payload.key]: action.payload.value })
// )
const user = createReducer<UserState, UserAction>(initialState, {
  [CHANGE_USER_TIMER_TYPE]: (state, action) => ({
    ...state,
    userTimer: {
      ...state.userTimer,
      selectedType: action.payload.selectedType,
      time: action.payload.time,
    },
  }),
  [CHANGE_USER_TIMER_STATUS]: (state, action) => ({
    ...state,
    userTimer: {
      ...state.userTimer,
      status: action.payload,
    },
  }),
  [CHANGE_USER_TIMER_TIME]: (state, action) => ({
    ...state,
    userTimer: {
      ...state.userTimer,
      time: action.payload,
    },
  }),
})
// ).handleAction(
//   transformToArray(userTimerAsync),
//   createAsyncReducer(userTimerAsync, "userTimer")
// );

export default user