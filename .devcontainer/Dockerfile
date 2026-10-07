FROM mcr.microsoft.com/devcontainers/java:1-21-bookworm
USER root
RUN grep -rl "yarnpkg" /etc/apt/sources.list.d/ 2>/dev/null | xargs -r rm -f
