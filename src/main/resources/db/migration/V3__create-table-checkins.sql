CREATE TABLE check_ins (
    id SERIAL NOT NULL PRIMARY KEY ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    attendee_id VARCHAR(255) NOT NULL,
    CONSTRAINT check_ins_attendee_id_fk FOREIGN KEY (attendee_id) REFERENCES attendees (id) ON DELETE RESTRICT ON UPDATE CASCADE
);