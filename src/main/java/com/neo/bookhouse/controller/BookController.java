/**
 * @Author: Neo
 * @Date: 2022/09/01 星期四 14:16:54
 * @Project: book_house
 * @IDE: IntelliJ IDEA
 **/
package com.neo.bookhouse.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neo.bookhouse.common.BaseContext;
import com.neo.bookhouse.common.R;
import com.neo.bookhouse.entity.Book;
import com.neo.bookhouse.entity.Booktag;
import com.neo.bookhouse.service.BookService;
import com.neo.bookhouse.service.BookTagService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/book")
@Slf4j
public class BookController {

    @Autowired
    BookService bookService;
    
    @Autowired
    BookTagService bookTagService; //标签的修改
    
    public static final int pageSize = 8; //按标签分页的大小为8

    public void bookTagFunc(Booktag booktag, String isbn, String kind, int mode)//mode(0为增加，1为删除)
    {
    	int sum;
    	if(mode == 0)//增加
    	{
    		if(booktag == null)//记录为空
	     	{
	     	    //添加最新的标签
	     	   booktag = Booktag.builder().bookIsbn(isbn).bookKind(kind).bookSum(1).build();
	     	   bookTagService.save(booktag);
	     	 }
	    	else{//标签数量+1
	    	 sum = booktag.getBookSum();
	    	 System.out.println("sum: "+sum);
	    	 LambdaQueryWrapper<Booktag>wrapper = new LambdaQueryWrapper<>();
	    	 wrapper.eq(Booktag::getBookIsbn, isbn);
	    	 wrapper.eq(Booktag::getBookKind, kind);
	    	 booktag = Booktag.builder().bookIsbn(isbn).bookKind(kind).bookSum(sum+1).build();
	    	 bookTagService.update(booktag,wrapper);
	    	}
    	}
    	else{//删除
    		if(booktag != null)//记录不为空才能删除
    		{
    		sum = booktag.getBookSum();
    		LambdaQueryWrapper<Booktag>wrapper = new LambdaQueryWrapper<>();
	    	wrapper.eq(Booktag::getBookIsbn, isbn);
	    	wrapper.eq(Booktag::getBookKind, kind);
	    	if(sum <= 1)	
	    	bookTagService.remove(wrapper);//数量小于1，删除标签
	    	else 
	    	{
	    	booktag = Booktag.builder().bookIsbn(isbn).bookKind(kind).bookSum(sum-1).build();
			bookTagService.update(booktag,wrapper);//数量-1
    		}
    		}
    	}
    	
    }
    
    //自动根据标签更新书籍种类的函数(kind1表示原来的KIND,kind2表示最新的KIND)
    public void changeBookKindByTag(String isbn, String kind1, String kind2, int mode)//0表示增加，1表示修改，2表示删除
    {
    	if(isbn != null && kind1 != null)
    	{
    		//查找原来种类对应的标签
    		LambdaQueryWrapper<Booktag> wrapper = new LambdaQueryWrapper<>();
    		System.out.println(isbn+" "+kind1);
    		wrapper.eq(Booktag::getBookIsbn, isbn);
    		wrapper.eq(Booktag::getBookKind, kind1);
    	    Booktag booktag = bookTagService.getOne(wrapper);
    	    //System.out.println(booktag.toString());
    	    
    	        //更新标签
    	    	switch(mode)
    	    	{
    	    	case 0://增加
    	    	bookTagFunc(booktag, isbn, kind2, 0);
    	    	break;
    	    	
    	    	case 1://修改
    	    	//让原来的标签数量-1
    	    	bookTagFunc(booktag, isbn, kind1, 1);
    	    	
    	    	//让最新的标签数量+1
    	    	//查找最新的标签
    	    	wrapper = new LambdaQueryWrapper<>();
        		wrapper.eq(Booktag::getBookIsbn, isbn);
        		wrapper.eq(Booktag::getBookKind, kind2);
        	    booktag = bookTagService.getOne(wrapper);
        	   
        	    bookTagFunc(booktag, isbn, kind2, 0);
    	    	break;
    	    	
    	    	case 2://删除
    	    	bookTagFunc(booktag, isbn, kind2, 1);
    	    	break;
    	    	}
    	    
    	    //确定书籍的种类
    	    //找出该ISBN号对应的标签数量最大值，并将这些书籍的种类改为该标签
    	    	//可能删没了。
    	    booktag = bookTagService.getOne(new LambdaQueryWrapper<Booktag>().orderByDesc(Booktag::getBookSum).last("limit 1"));
    	    if(booktag != null)
    	    {
    	    	System.out.println("booktag:"+isbn+" "+kind2);
    	    isbn = booktag.getBookIsbn();
    	    kind2 = booktag.getBookKind();
    	    LambdaQueryWrapper<Book>wrapper2 = new LambdaQueryWrapper<Book>();
    	    wrapper2.eq(Book::getBookIsbn, isbn);
    	    List<Book>books = bookService.list(wrapper2);
    	    for(Book b : books)
    	    {
    	    	Book book2 = new Book();
    	    	book2.setBookId(b.getBookId());
    	    	book2.setBookIsbn(isbn);
    	    	book2.setBookTag(kind2);
    	    	bookService.updateById(book2);
    	    }
    	    }
    	}
    }
    
    @PostMapping("/add")//新增书籍
    public R<String> add(@RequestBody Book book, HttpServletRequest request) {
        log.info("添加书籍：{}", book);
        //String userId = request.getAttribute("userId").toString();
        //book.setUserId(userId);
        //book.setUserId(1L);
        book.setUserId(BaseContext.getCurrentId());
        
        //获取书籍的ISBN号和书的类型，用来动态修改标签
        String isbn = null, kind = null;
        if(book != null)
        {
        isbn = book.getBookIsbn();
        kind = book.getBookKind();
        }
        
        boolean flg = bookService.save(book);
        if (flg) {
        	changeBookKindByTag(isbn, kind, kind, 0);//增加标签
            return R.success("添加成功");
        }
        return R.error("添加失败");
    }

    @DeleteMapping("/delete/{id}")//删除书籍
    public R<String> delete(@PathVariable Long id)//路径变量
    {
    	log.info("删除{}",id);
    	LambdaQueryWrapper<Book>wrapper = new LambdaQueryWrapper<>();
    	wrapper.eq(Book::getBookId, id);
    	Book book = bookService.getOne(wrapper);
    	log.info(book.toString());
    	String kind = null,isbn = null;
    	if(book != null)
    	{
        kind = book.getBookKind();//要删除书的种类
    	isbn = book.getBookIsbn(); 
    	}
        boolean flg = bookService.removeById(id);
        if(flg)
        {
        	changeBookKindByTag(isbn, kind, kind, 2);//删除标签
        }
        return R.success("删除成功");
    }
    
    @PutMapping("/update")//编辑书籍
     public R<String>update(@RequestBody Book book)
     {
        //查询该书的ID
    	LambdaQueryWrapper<Book>wrapper = new LambdaQueryWrapper<>();
    	wrapper.eq(Book::getBookId, book.getBookId());
        String kind = bookService.getOne(wrapper).getBookKind();//原来的种类
        String kind2 = book.getBookKind();//要修改的种类
    	String isbn = book.getBookIsbn();
    	boolean flg = bookService.updateById(book);
    	//可能修改对应的标签
    	if(flg)
    	{
    		if(!kind.equals(kind2))//表明要修改种类
    		changeBookKindByTag(isbn, kind, kind2, 1);//修改
    	}
    	return R.success("修改成功");
     }
    
    @PostMapping("/cover/{id}")//上传书籍封面("bookId")
    public R<Book> getBookByBookId(@PathVariable Long id)
    {
    	LambdaQueryWrapper<Book>queryWrapper = new LambdaQueryWrapper<>();
    	queryWrapper.eq(Book::getBookId, id);
    	return R.success(bookService.getOne(queryWrapper));
    }
    
    @GetMapping("/findAll/{id}")//查询书屋的信息("userId")
    public R<List<Book>> getBookByUserId(@PathVariable Long id)//路径变量
    {
    	LambdaQueryWrapper<Book>queryWrapper = new LambdaQueryWrapper<>();
    	queryWrapper.eq(Book::getUserId, id);
    	return R.success(bookService.list(queryWrapper));
    }
    
    @GetMapping("/findByName/{bookName}")//通过字符串查找书名
    public R<List<Book>> getBookByName(@PathVariable String bookName)
    {
    	LambdaQueryWrapper<Book>queryWrapper = new LambdaQueryWrapper<>();
    	queryWrapper.like(Book::getBookName, bookName);
    	return R.success(bookService.list(queryWrapper));
    }
    
    @GetMapping("/findByTag/{bookTag}/{page}")//通过书籍标签查找书名,参数为标签和页号
    public R<Page<Book>> getBookByTag(@PathVariable String bookTag, @PathVariable int page)
    {
    	//分页构造器
    	Page<Book>pageBuilder = new Page<>(page, pageSize);
    	//查询条件
    	LambdaQueryWrapper<Book>queryWrapper = new LambdaQueryWrapper<>();
    	//根据书ID排序，查找和标签相同的书籍
    	queryWrapper.orderByAsc(Book::getBookId);
    	
    	queryWrapper.eq(Book::getBookTag, bookTag);
    	
    	Page<Book>result = bookService.page(pageBuilder, queryWrapper);
    	
    	return R.success(result);
    }
    

}
