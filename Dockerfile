FROM ubuntu:18.04

USER root

RUN apt-get update \
    && apt-get upgrade --assume-yes \
    && apt-get install -y software-properties-common git vim tree jq ncdu

RUN echo "deb https://dl.bintray.com/sbt/debian /" | tee -a /etc/apt/sources.list.d/sbt.list \
    && apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823 \
    && apt-get update \
    && apt-get install --no-install-recommends -y openjdk-11-jdk \
    && apt-get clean

RUN apt-get install --no-install-recommends -y sbt=1.3.0

# WORKAROUND: it seems, that sbt has introduced a bug (or changed its behaviour)
# That means, the command for the crossbuild (sbt "+update") doesn't work anymore as it used to.

# Add and use user sbtuser
RUN groupadd --gid 1000 sbtuser && useradd --gid 1000 --uid 1000 sbtuser --shell /bin/bash
RUN chown -R sbtuser:sbtuser /opt
RUN mkdir /home/sbtuser && chown -R sbtuser:sbtuser /home/sbtuser
RUN mkdir /logs && chown -R sbtuser:sbtuser /logs

USER sbtuser

RUN mkdir /home/sbtuser/sbt-crossbuild-broken
COPY --chown=sbtuser:sbtuser project/ /home/sbtuser/sbt-crossbuild-broken/project/
COPY --chown=sbtuser:sbtuser sampleProject/ /home/sbtuser/sbt-crossbuild-broken/sampleProject/

WORKDIR /home/sbtuser/sbt-crossbuild-broken

RUN sbt "+update"

WORKDIR /home/sbtuser/


#RUN rm -r /home/sbtuser/sbt-crossbuild-broken
