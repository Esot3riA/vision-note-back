#!/bin/bash

REPOSITORY=/home/ubuntu/apps
DEPLOY_DIRECTORY=/home/ubuntu/deploy
PROJECT_NAME=visionnote-back

echo "> Build 파일 복사"

cp $DEPLOY_DIRECTORY/$PROJECT_NAME/*.jar $REPOSITORY/$PROJECT_NAME/

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -fl $PROJECT_NAME | grep jar | awk '{print $1}')

echo "> 현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
   echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
   echo "> kill -15 $CURRENT_PID"
   kill -15 $CURRENT_PID
   sleep 5
fi

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/$PROJECT_NAME/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

rm $REPOSITORY/$PROJECT_NAME/nohup.out

nohup java -jar -Dspring.profiles.active=alpha -Dfile.encoding=UTF-8  $JAR_NAME > $REPOSITORY/$PROJECT_NAME/nohup.out 2>&1 &