-- 대시보드 단계별 건수 출력 SQL
SELECT 
    (SELECT Count(ordernum)
    FROM   order_history
    WHERE  statusnum = ?
            AND To_char(order_date, 'YYYY-MM-DD') =
                To_char(sysdate, 'YYYY-MM-DD') AND cancelNum IS NULL)
    status1,
    (SELECT Count(ordernum)
    FROM   order_history
    WHERE  statusnum = ?
            AND To_char(order_date, 'YYYY-MM-DD') =
                To_char(sysdate, 'YYYY-MM-DD') AND cancelNum IS NULL)
    status2,
    (SELECT Count(ordernum)
    FROM   order_history
    WHERE  statusnum = ?
            AND To_char(order_date, 'YYYY-MM-DD') =
                To_char(sysdate, 'YYYY-MM-DD') AND cancelNum IS NULL)
    status3,
    (SELECT Count(ordernum)
    FROM   order_history
    WHERE  statusnum = ?
            AND To_char(order_date, 'YYYY-MM-DD') =
                To_char(sysdate, 'YYYY-MM-DD') AND cancelNum IS NULL)
    status4
FROM   dual  


-- 대시보드 오늘의 베스트 셀러와 총 매출액
select * from(
SELECT * FROM(
SELECT rownum rnum, menuName todayMenuName, quantity, thumbnail FROM (
SELECT menuName, od.menuNum, thumbnail, SUM(quantity) quantity
from order_detail od
JOIN order_history oh ON od.orderNum = oh.orderNum
JOIN menu mn ON od.menuNum = mn.menuNum
WHERE TO_CHAR(order_date,'YYYY-MM-DD') = TO_CHAR(SYSDATE, 'YYYY-MM-DD') AND cancelNum IS NULL
group by (od.menuNum, menuName, thumbnail)
ORDER BY quantity DESC)) WHERE rnum=1 ), (SELECT SUM(unitPrice*quantity) todayTotalSales 
FROM order_detail od
JOIN order_history oh ON od.orderNum = oh.orderNum
WHERE TO_CHAR(order_date, 'YYYY-MM-DD') = TO_CHAR(SYSDATE, 'YYYY-MM-DD') AND cancelNum IS NULL);




--연습

--오늘의 매출 SQL
SELECT SUM(unitPrice) 
FROM order_detail od
JOIN order_history oh ON od.orderNum = oh.orderNum
WHERE TO_CHAR(order_date, 'YYYY-MM-DD') = TO_CHAR(SYSDATE, 'YYYY-MM-DD');

--오늘의 베스트 셀러 (품목명, 품목번호, 수량, 주문금액 총)
SELECT * FROM( 
SELECT rownum rnum, menuName, menuNum, quantity, total FROM (
SELECT menuName, od.menuNum, SUM(quantity) quantity, sum(quantity*unitPrice) total
from order_detail od
JOIN order_history oh ON od.orderNum = oh.orderNum
JOIN menu mn ON od.menuNum = mn.menuNum
WHERE TO_CHAR(order_date,'YYYY-MM-DD') = TO_CHAR(SYSDATE, 'YYYY-MM-DD')
group by (od.menuNum, menuName)
ORDER BY quantity DESC)) WHERE rnum=1;

--오늘 품목별 판매현황 (메뉴이름 포함)
SELECT menuName, od.menuNum, SUM(quantity) quantity, SUM(quantity*price) "매출액"
from order_detail od
JOIN order_history oh ON od.orderNum = oh.orderNum
JOIN menu mn ON od.menuNum = mn.menuNum
WHERE TO_CHAR(order_date,'YYYY-MM-DD') = TO_CHAR(SYSDATE, 'YYYY-MM-DD')
group by (od.menuNum, menuName)
ORDER BY quantity DESC; -- 메뉴별 

