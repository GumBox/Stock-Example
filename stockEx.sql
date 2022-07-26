
create database stockEx


create table Stocks (
    sIDStock NVARCHAR(10) PRIMARY KEY
)

create table Traders (
    sIDTrader NVARCHAR(10) PRIMARY KEY
)

create table Orders (
    type_stock NVARCHAR(5),
    sIDStock NVARCHAR(10),
    sIDTrader NVARCHAR(10),
    amount INT,
    price FLOAT
    CONSTRAINT fk_idStock FOREIGN key (sIDStock) REFERENCES Stocks(sIDStock),
    CONSTRAINT fk_idTrader FOREIGN key (sIDTrader) REFERENCES Traders(sIDTrader)
)

create table Transactions (
    transID INT PRIMARY KEY,
    idStock NVARCHAR(10),
	idBuyer NVARCHAR(10),
	idSeller NVARCHAR(10),
	amount INT,
	price FLOAT,
	trans_date date,
  CONSTRAINT fk_idStocks FOREIGN key (idStock) REFERENCES Stocks(sIDStock),
    CONSTRAINT fk_idBuyer FOREIGN key (idBuyer) REFERENCES Traders(sIDTrader) ,
	CONSTRAINT fk_idSeller FOREIGN key (idSeller) REFERENCES Traders(sIDTrader) ,
	
)


