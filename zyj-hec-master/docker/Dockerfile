# HOW TO BUILD THIS BASE IMAGE
# registry.saas.hand-china.com/library/tomcat:8.5 is the same as tomcat:8.5
# -----------------------
# FROM registry.saas.hand-china.com/library/tomcat:8.5
# RUN rm -rf webapps/zyjhec
# RUN apt-get update && apt-get install -y --no-install-recommends \
# 		xmlstarlet \
# 	&& rm -rf /var/lib/apt/lists/*
#
# Run: 
#      $ docker build -t registry.saas.hand-china.com/library/tomcat:8.5-1 . 

FROM registry.saas.hand-china.com/library/tomcat:8.5-1
RUN echo "Asia/shanghai" > /etc/timezone;
RUN rm -rf webapps
COPY zyjhec.war  webapps/
RUN unzip -q webapps/zyjhec.war -d webapps/zyjhec
COPY build/catalina.properties conf/
COPY entrypoint.sh .

ENTRYPOINT ["sh","./entrypoint.sh"]