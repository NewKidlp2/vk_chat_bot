# ������ �������:

## JDK:
1. ���������� jdk (������� 8 ������) � [�����](https://www.oracle.com/java/technologies/javase-downloads.html])
2. �������� ���������� �����: �������� ������ ���������� -> ������� � ������������ -> ������� -> ��������������
��������� ������� -> ���������� �����.
3. ���������, ��� ���������� bin JDK ���������� � ���������� PATH (��������� ����������, ��� ����� ����������
������� ���������� ����� � ������� ���������� PATH, ���� � ���, �� ���������� ��������.
## ngrok:
1. �������� ngrok � [�����](https://ngrok.com/download) � ���������.
2. � ����������� ��������� ������ �������� `ngrok http {*port_number*}`, ��� `{port_number}`, ��� ��������� ����
�� ����� ���������� (�� ��������� 8081).
## ���� ������������:
1. �������� ���� ������������ `application.properties`, ����������� �� ���� `{*project folder*}/src/main/resources`.
2. � �������� ����� `server.port` ������� ����, ��������� ���� ��� ��������� ngrok.
3. � ���� `server.url` ������� `url`, ��������������� ���������� `ngrok`.
4. �� ������� ��������� � �������� ���������� � � ��������� ����.
## ��������������� ������:
1. �������� ��������� ������ � ������������� � ���������� �������.
2. ������� ������� `gradle run`. ��� ����������� ���� ������� ��������� ������������� ������� Gradle.