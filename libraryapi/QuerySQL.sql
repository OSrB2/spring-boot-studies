CREATE TABLE tb_author(
	id UUID NOT NULL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  date_birth DATE NOT NULL,
  nationality VARCHAR(50) NOT NULL
)

CREATE TABLE tb_book (
  id UUID NOT NULL PRIMARY KEY,
  isbn VARCHAR(20) NOT NULL,
  title VARCHAR(150) NOT NULL,
  publication_date DATE NOT NULL,
  gender VARCHAR(30) NOT NULL,
  price NUMERIC(18,2),
  id_author UUID NOT NULL REFERENCES tb_author(id),
  CONSTRAINT chk_gender CHECK (gender IN ('FICTION', 'FANTASY', 'MYSTERY', 'ROMANCE', 'BIOGRAPHY', 'SCIENCE'))
);
