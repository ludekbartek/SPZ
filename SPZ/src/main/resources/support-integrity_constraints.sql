/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  bar
 * Created: Apr 2, 2019
 */
-------------------------------------------------------------------------------------------
-- Foreign keys for relation tables
-------------------------------------------------------------------------------------------
alter table attachmentnote add constraint attnote01 foreign key (attachmentid) references ATTACHMENT(id);

alter table attachmentnote add constraint attnote02 foreign key (spznoteid) references SPZNOTE(id);

alter table configurationspz add constraint confspz01 foreign key (configurationid) references CONFIGURATION(id);

alter table configurationspz add constraint confspz02 foreign key (spzid) references SPZ(id);

alter table noteissuer add constraint noteis01 foreign key (noteid) references SPZNOTE(id);

alter table noteissuer add constraint noteis02 foreign key (userid) references USER_(id);

alter table projectconfiguration add constraint projconf01 foreign key (projectid) references PROJECT(id);

alter table projectconfiguration add constraint projconf02 foreign key (configurationid) references CONFIGURATION(id);

alter table roles add constraint roles01 foreign key (USERID) references USER_(id);

alter table SPZISSUER add constraint spzissuer01 foreign key (SPZID) references SPZ(id);

alter table SPZISSUER add constraint spzissuer02 foreign key (USERID) references USER_(id);

alter table spzstatenote add constraint spzstatenote01 foreign key (NOTEID) references SPZNOTE(ID);

alter table spzstatenote add constraint spzstatenote02 foreign key (STATEID) references SPZSTATE(ID);

alter table spzstates add constraint spzstates01 foreign key (SPZID) references SPZ(ID);

alter table spzstates add constraint spzstates02 foreign key (STATEID) references SPZSTATE(ID);

alter table useraccess add constraint useraccess01 foreign key (USERID) references USER_(id);

alter table useraccess add constraint useraccess02 foreign key (CONFIGURATIONID) references CONFIGURATION(ID);
