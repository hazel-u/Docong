package com.b5f1.docong.core.queryrepository;

import com.b5f1.docong.core.domain.group.TeamUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.b5f1.docong.core.domain.group.QTeamUser.teamUser;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamUserQueryRepositoryImpl implements TeamUserQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<TeamUser> findTeamUserWithTeamId(Long teamSeq) {
        return query
                .select(teamUser)
                .from(teamUser)
                .where(teamUser.team.seq.eq(teamSeq))
                .fetch();
    }

    @Override
    public List<TeamUser> findTeamUserWithUserId(Long userSeq) {
        return query
                .select(teamUser)
                .from(teamUser)
                .where(teamUser.user.seq.eq(userSeq))
                .fetch();
    }

    @Override
    public Optional<TeamUser> findTeamUserWithUserIdAndTeamId(Long userSeq, Long teamSeq) {
        return Optional.ofNullable(query
                .selectFrom(teamUser)
                .where(teamUser.user.seq.eq(userSeq),teamUser.team.seq.eq(teamSeq))
                .fetchOne());
    }

    @Override
    public Long findLeaderIdWithTeamId(Long teamSeq) {
        return query
                .selectFrom(teamUser)
                .where(teamUser.leader.eq(true))
                .fetchOne()
                .getSeq();
    }
}
