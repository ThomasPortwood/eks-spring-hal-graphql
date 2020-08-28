delimiter $$
create trigger create_fixture_trigger
    after insert
    on fixture
    for each row
begin
    insert into event(parent_id, attributes)
    values (new.id,
            concat('{',
                   '"type": "CREATE",',
                   '"description": "Created fixture ', new.name, '"'
                       '}'));
end
$$
delimiter ;