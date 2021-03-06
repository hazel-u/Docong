import Api from '../lib/customApi'
// import { modifyJiraInfo } from '../modules/group'
import { BASE_URL } from './auth'
import { UserInfo } from './user'

export async function createGroup(groupCreateData: GroupCreateData) {
  const response = await Api.post(`${BASE_URL}/api/team`, groupCreateData)
  return response.data
}

export async function modifyGroup(groupModifyData: GroupModifyData) {
  const response = await Api.put(`${BASE_URL}/api/team`, groupModifyData)
  return response.data
}

export async function deleteGroup(team_id: number) {
  const response = await Api.delete(`${BASE_URL}/api/team/${team_id}`)
  return response.data
}

export async function searchGroup(team_id: number) {
  const response = await Api.get(`${BASE_URL}/api/team/${team_id}`)
  return response.data
}

export async function searchAllGroup() {
  const response = await Api.get(`${BASE_URL}/api/team/all`)
  return response.data
}

export async function addMemberGroup(
  groupMemberModifyData: GroupMemberModifyData
) {
  const response = await Api.post(
    `${BASE_URL}/api/team/member`,
    groupMemberModifyData
  )
  return response.data
}

export async function deleteMemberGroup(
  groupMemberModifyData: GroupMemberModifyData2
) {
  const response = await Api.delete(
    `${BASE_URL}/api/team/member/${groupMemberModifyData.team_id}/${groupMemberModifyData.user_email}`
  )
  return response.data
}

export async function getUserListData(team_id: number) {
  const response = await Api.get(`${BASE_URL}/api/team/activate/${team_id}`)
  return response.data
}

export async function modifyJiraInfo(jiraData: JiraData, team_id: number) {
  const response = await Api.post(`${BASE_URL}/api/jira/${team_id}`, jiraData)
  return response.data
}

export async function getJiraIssue(team_id: number) {
  const response = await Api.get(`${BASE_URL}/api/jira/${team_id}`)
  return response.data
}

export interface JiraData {
  jiraDomain: string
  jiraUserId: string
  jiraAPIToken: string
  jiraProjectKey: string
}

export interface GroupCreateData {
  userEmail: string
  name: string
}

export interface GroupModifyData {
  userEmail: string
  teamId: number
  name: string
}

export interface GroupMemberModifyData {
  teamId: number
  userEmail: string
}

export interface GroupMemberModifyData2 {
  team_id: number
  user_email: string
}

export interface OnOffUser {
  online: boolean
  userEmail: string
  userImg: string
  userName: string
}

export interface OnOffUserList extends Array<OnOffUser> {}

export interface GroupData {
  teamId: number
  userList: Array<UserInfo>
  name: String
  leader: UserInfo
}

export interface DefaultResponse {
  code: string
  message: string
  status: number
}

export interface MemberData {
  email: string
  image: string
  name: string
}

export interface Group {
  teamSeq: number
  jiraApiToken: string
  jiraDomain: string
  jiraProjectKey: string
  jiraUserId: string
  userList: Array<MemberData> | null
  name: string
  leaderEmail: string
}

export interface Groups extends Array<Group> {}
