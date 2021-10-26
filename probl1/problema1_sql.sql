
-- cerinta 1
select count(id_farmacie) as Comenzi,sum(A.cantitate * B.pret_buc) as Suma_Totala,avg(A.cantitate * B.pret_buc) as val_medie_per_comanda from comanda as A,depozit as B where A.iddepozit=B.iddepozit and nume_farmacie='Dona' and MONTH(A.data_comanda) = 8 group by id_farmacie;

-- cerinta 2
SELECT count(*) as Comenzi_antibiotice from comanda as A JOIN depozit as B ON A.iddepozit=B.iddepozit WHERE nume_farmacie='Vlad' and YEAR(A.data_comanda)=2021 and B.categorie='antibiotice';

-- cerinta 3
SELECT nume_farmacie,count(id_farmacie) AS Farmacia_Anului FROM comanda AS A join depozit AS B on A.iddepozit=B.iddepozit WHERE YEAR(A.data_comanda) = 2021 group by id_farmacie order by Farmacia_Anului desc limit 1;


select * from comanda;
