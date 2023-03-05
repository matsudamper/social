FROM eclipse-temurin:17-jre-jammy
RUN apt update
RUN apt install  -y --allow-unauthenticated nginx

RUN mkdir app app/backend app/frontend

COPY ./backend/build/distributions/social.tar ./
RUN tar -xf social.tar -C app/backend/
RUN rm social.tar

COPY ./frontend/jsApp/build/distributions/* app/frontend/

CMD nginx