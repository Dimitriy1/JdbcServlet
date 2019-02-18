alter table developer add salary double;

select project.name, sum(developer.salary) as sumOfSalaries from project
inner join developer_project
on project.id = developer_project.project_id
inner join developer
on developer.id = developer_project.developer_id
group by project.name
order by sumOfSalaries desc
limit 1;

select sum(developer.salary) as SumJavaDevSalary
from developer inner join developer_skill
on developer.id = developer_skill.developer_id
inner join skill
on skill.id = developer_skill.skill_id
where skill.language = "java";

alter table project add cost double;

select * from project where project.cost = (
select min(project.cost) from project);

select avg(developer.salary) as AvgJavaDevSalary
from developer inner join developer_project
on developer.id = developer_project.developer_id
inner join project
on project.id = developer_project.project_id
where project.cost = (
select min(project.cost) from project);
