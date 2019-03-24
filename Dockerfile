FROM payara/micro
RUN mkdir /opt/payara/data && chown payara /opt/payara/data
COPY ./target/finance-service-1.0-SNAPSHOT.war $DEPLOY_DIR