import { Box, Button, Dialog, DialogActions, DialogContent, DialogTitle, Grid, TextField } from "@mui/material"
import { ChangeEvent, FormEvent, useState } from "react"
import { useDispatch, useSelector } from "react-redux"
import { useNavigate } from "react-router-dom"
import { Group, GroupCreateData } from "../../api/group"
import { RootState } from "../../modules"
import { createGroupAsync } from "../../modules/group"

interface GroupCreateFormProps {
    isOpenGroupCreateForm: boolean
    closeGroupCreateForm: () => void
    onCreateGroupSubmit: (groupCreateData: GroupCreateData) => void
}

function GroupCreateForm({
    isOpenGroupCreateForm,
    closeGroupCreateForm,
    onCreateGroupSubmit
}: GroupCreateFormProps) {
    const userInfo = useSelector((state:RootState) => state.user.userInfo.data)

    const [createGroupInfo, setCreateGroupInfo] = useState({
        userEmail: userInfo ? userInfo.email: '',
        name: ''
    })

    const navigate = useNavigate()
    const dispatch = useDispatch()

    const onChangeName = (e: ChangeEvent<HTMLInputElement>) => {
        setCreateGroupInfo({ ...createGroupInfo, name: e.target.value })
    }

    const onSubmitCreateGroup = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        if (createGroupInfo.userEmail === '' || createGroupInfo.name === '') {
            alert('유효하지 않은 입력이 존재합니다.')
        } else {
            onCreateGroupSubmit(createGroupInfo)
        }
    }

    return (
        <div>
            <Dialog open={isOpenGroupCreateForm} onClose={closeGroupCreateForm}>
                <DialogTitle>Create Group</DialogTitle>
                <Box component="form" onSubmit={onSubmitCreateGroup}>
                    <DialogContent
                        sx={{
                            fontSize: '24px',
                            fontWeight: 'bold',
                            p: '28px',
                            pb: '4px',
                            color: (theme) => theme.colors.greenText,
                        }}>
                        <Grid container>
                            <Grid item xs={5}>
                                <Box sx={{
                                    display: 'flex',
                                    height: '56px',
                                    ml: '10px',
                                    mb: '14px',
                                    justifyContent: 'start',
                                    alignItems: 'center',
                                    fontSize: '20px',
                                    fontWeight: 'bold',
                                }}>
                                    <div>Group Name</div>
                                </Box>
                            </Grid>
                            <Grid item xs={7}>
                                <TextField
                                    required
                                    fullWidth
                                    id="jiraDomain"
                                    variant="outlined"
                                    onChange={onChangeName}
                                    value={createGroupInfo.name}
                                    color="success"
                                    sx={{ mb: '14px' }}
                                    inputProps={{ maxLength: 15 }}
                                />
                            </Grid>

                        </Grid>
                    </DialogContent>
                    <DialogActions sx={{ p: '0 24px 20px 24px' }}>
                        <Button
                            sx={{
                                width: '22%',
                                fontSize: '16px',
                                color: (theme) => theme.colors.pageBg,
                                background: (theme) => theme.colors.greenButton,
                                borderRadius: '8px',
                                mr: '8px',
                            }}
                            variant="contained"
                            color="success"
                            type="submit"
                        >
                            생성
                        </Button>
                        <Button
                            sx={{
                                width: '22%',
                                fontSize: '16px',
                                color: (theme) => theme.colors.pageBg,
                                background: (theme) => theme.colors.gray,
                                borderRadius: '8px',
                            }}
                            onClick={closeGroupCreateForm}
                            variant="contained"
                            color="success"
                        >
                            취소
                        </Button>
                    </DialogActions>
                </Box>
            </Dialog>
        </div>
    )
}

export default GroupCreateForm