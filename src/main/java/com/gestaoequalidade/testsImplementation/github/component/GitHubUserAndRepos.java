package com.gestaoequalidade.testsImplementation.github.component;

import com.gestaoequalidade.testsImplementation.github.entity.GitHubRepository;
import com.gestaoequalidade.testsImplementation.github.entity.GitHubUser;

public class GitHubUserAndRepos {
    private final GitHubUser user;
    private final GitHubRepository[] repositories;

    public GitHubUserAndRepos(GitHubUser user, GitHubRepository[] repositories) {
        this.user = user;
        this.repositories = repositories;
    }

    public GitHubUser getUser() {
        return user;
    }

    public GitHubRepository[] getRepositories() {
        return repositories;
    }
}
