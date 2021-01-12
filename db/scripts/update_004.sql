--Add column to table item
ALTER TABLE todolist.public.item add column user_id int references todolist.public.j_user(id);