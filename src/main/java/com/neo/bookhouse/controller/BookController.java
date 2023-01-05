/**
 * @Author: Neo
 * @Date: 2022/09/01 星期四 14:16:54
 * @Project: book_house
 * @IDE: IntelliJ IDEA
 **/
package com.neo.bookhouse.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neo.bookhouse.common.R;
import com.neo.bookhouse.dto.BookDto;
import com.neo.bookhouse.entity.Book;
import com.neo.bookhouse.entity.Booktag;
import com.neo.bookhouse.entity.User;
import com.neo.bookhouse.service.BookService;
import com.neo.bookhouse.service.BookTagService;
import com.neo.bookhouse.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
@Slf4j
public class BookController {

    @Autowired
    BookService bookService;
    @Autowired
    BookTagService bookTagService; //标签的修改
    @Autowired
    UserService userService; //用于用户地理位置查找

    public void bookTagFunc(Booktag booktag, String isbn, Integer kind, int mode)//mode(0为增加，1为删除)
    {
        if (booktag == null)//标签记录为空
        {
            booktag = Booktag.builder().bookIsbn(isbn).build();
            bookTagService.save(booktag);//先存放标签
        }
        booktag = bookTagService.getOne(new LambdaQueryWrapper<Booktag>().eq(Booktag::getBookIsbn, isbn));
        LambdaQueryWrapper<Booktag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Booktag::getBookIsbn, isbn);
        //数量数组
        int[] kindSum = {
                booktag.getEducationSum(),
                booktag.getLiteratureArtSum(),
                booktag.getComicHumorSum(),
                booktag.getYouthSum(),
                booktag.getChildrenSum(),
                booktag.getSocialScienceSum(),
                booktag.getLifeSum(),
                booktag.getTechnologySum(),
                booktag.getFictionSum()
        };

        if (kindSum[kind] == 0 && mode == 1)//数量是0时不能再删除
            return;
        else kindSum[kind] += (mode == 0 ? 1 : -1);//数量更新

        //确定最大值的种类
        int MaxSum = 0, MaxIndex = 0;//初始值为教育类
        for (int i = 0; i < kindSum.length; i++)
            if (kindSum[i] > MaxSum) {
                MaxSum = kindSum[i];
                MaxIndex = i;
            }

        if (MaxSum == 0)//全部都为0,表示空标签则删除
            bookTagService.remove(wrapper);
        else {

            switch (kind) {
                case 0://教育类
                    booktag = Booktag.builder().educationSum(kindSum[0]).bookKind(MaxIndex).build();
                    break;
                case 1://文艺类
                    booktag = Booktag.builder().literatureArtSum(kindSum[1]).bookKind(MaxIndex).build();
                    break;
                case 2://动漫幽默类
                    booktag = Booktag.builder().comicHumorSum(kindSum[2]).bookKind(MaxIndex).build();
                    break;
                case 3://青春类
                    booktag = Booktag.builder().youthSum(kindSum[3]).bookKind(MaxIndex).build();
                    break;
                case 4://童书类
                    booktag = Booktag.builder().childrenSum(kindSum[4]).bookKind(MaxIndex).build();
                    break;
                case 5://人文社科类
                    booktag = Booktag.builder().socialScienceSum(kindSum[5]).bookKind(MaxIndex).build();
                    break;
                case 6://生活类
                    booktag = Booktag.builder().lifeSum(kindSum[6]).bookKind(MaxIndex).build();
                    break;
                case 7://科技类
                    booktag = Booktag.builder().technologySum(kindSum[7]).bookKind(MaxIndex).build();
                    break;
                case 8://科幻类
                    booktag = Booktag.builder().fictionSum(kindSum[8]).bookKind(MaxIndex).build();
                    break;
                default:
                    break;
            }
            bookTagService.update(booktag, wrapper);
        }

    }

    //自动根据标签更新书籍种类的函数(kind1表示原来的KIND,kind2表示最新的KIND)
    public void changeBookKindByTag(String isbn, Integer kind1, Integer kind2, int mode)//0表示增加，1表示修改，2表示删除
    {
        if (isbn != null && kind1 != null) {
            //查找原来种类对应的标签
            LambdaQueryWrapper<Booktag> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Booktag::getBookIsbn, isbn);
            Booktag booktag = bookTagService.getOne(wrapper);

            //更新标签
            switch (mode) {
                case 0://增加
                    bookTagFunc(booktag, isbn, kind2, 0);
                    break;

                case 1://修改
                    //让原来的标签数量-1
                    bookTagFunc(booktag, isbn, kind1, 1);

                    //让最新的标签数量+1
                    //之前更新过一遍，需要再查找一次
                    wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(Booktag::getBookIsbn, isbn);
                    booktag = bookTagService.getOne(wrapper);

                    bookTagFunc(booktag, isbn, kind2, 0);
                    break;

                case 2://删除
                    bookTagFunc(booktag, isbn, kind2, 1);
                    break;
            }
        }
    }

    @PostMapping("/add")//新增书籍
    public R<String> add(@RequestBody Book book, HttpServletRequest request) {
        log.info("添加书籍：{}", book);
        Long userId = Long.valueOf(request.getParameter("id"));
        System.out.println("id: " + userId);
        book.setUserId(userId);
        book.setBookBorrow(0);//刚添加的书籍没有被借走
        //book.setUserId(1L);
        //book.setUserId(BaseContext.getCurrentId());

        //获取书籍的ISBN号和书的类型，用来动态修改标签
        String isbn = null;
        Integer kind = null;
        if (book != null) {
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

    @DeleteMapping("/delete")//批量删除书籍
    public R<String> delete(@RequestParam List<Long> ids)//路径变量
    {
        log.info("删除{}", ids);

        Integer kind = null;
        String isbn = null;
        boolean flg = true, cur = true;//删除状态

        for (Long id : ids) {
            LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Book::getBookId, id);
            Book book = bookService.getOne(wrapper);

            if (book != null) {
                log.info(book.toString());
                kind = book.getBookKind();//要删除书的种类
                isbn = book.getBookIsbn();
                cur = bookService.removeById(id);
                if (cur)
                    changeBookKindByTag(isbn, kind, kind, 2);//删除标签

                flg &= cur;
            }
        }
        return flg ? R.success("删除成功") : R.error("删除失败");
    }

    @PutMapping("/update")//编辑书籍
    public R<String> update(@RequestBody Book book) {
        //查询该书的ID
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Book::getBookId, book.getBookId());
        Integer kind = bookService.getOne(wrapper).getBookKind();//原来的种类
        Integer kind2 = book.getBookKind();//要修改的种类
        String isbn = book.getBookIsbn();
        boolean flg = bookService.updateById(book);
        //可能修改对应的标签
        if (flg) {
            if (!kind.equals(kind2))//表明要修改种类
                changeBookKindByTag(isbn, kind, kind2, 1);//修改
            return R.success("修改成功");
        }
        return R.error("修改失败");
    }

    @GetMapping("/findAll/{id}")//查询书屋的信息("userId")
    public R<List<Book>> getBookByUserId(@PathVariable Long id)//路径变量
    {
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Book::getUserId, id);
        return R.success(bookService.list(queryWrapper));
    }

    //地理位置排序函数
    public Page<BookDto> getBookDtoByPage(Long userId, int page, Page<Book> pageBuilder) {
        Page<BookDto> dtoPage = new Page<>();
        BeanUtils.copyProperties(pageBuilder, dtoPage, "records");

        List<BookDto> bookDtos = pageBuilder.getRecords().stream().map(book -> {
            BookDto bookDto = new BookDto();
            BeanUtils.copyProperties(book, bookDto);
            Long bookKeeperId = book.getUserId();
            User bookKeeper = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserId, bookKeeperId));
            if (bookKeeper == null)//用户不存在
                return null;
            bookDto.setUserLatitude(bookKeeper.getUserLatitude());
            bookDto.setUserLongitude(bookKeeper.getUserLongitude());
            return bookDto;
        }).collect(Collectors.toList());

        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserId, userId));


        if (user != null) {
            Double longtitude = user.getUserLongitude(); //获取用户经度
            Double latitude = user.getUserLatitude(); //获取用户纬度
            if (longtitude != null && latitude != null)//经度和纬度都存在时候才能排序。
            {
                bookDtos.sort((o1, o2) -> {
                    Double dist1 = Math.sqrt(Math.pow(o1.getUserLongitude() - longtitude, 2) + Math.pow(o1.getUserLatitude() - latitude, 2));
                    Double dist2 = Math.sqrt(Math.pow(o2.getUserLongitude() - longtitude, 2) + Math.pow(o2.getUserLatitude() - latitude, 2));
                    return dist1.compareTo(dist2);
                });
            }
        }

        dtoPage.setRecords(bookDtos);
        return dtoPage;
    }

    @GetMapping("/listByName/{userId}/{bookName}/{page}/{pageSize}")//通过字符串查找书名，参数为借书者的id,查找的书名,页号和页面大小
    public R<Page> getListByName(@PathVariable Long userId, @PathVariable String bookName, @PathVariable int page, @PathVariable int pageSize) {
        //分页构造器
        Page<Book> pageBuilder = new Page<>(page, pageSize);
        List<Long> ids = bookService.getIdLikeName(bookName);
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Book::getBookId, ids);
        bookService.page(pageBuilder, queryWrapper);

        return R.success(getBookDtoByPage(userId, page, pageBuilder));
    }

    @GetMapping("/findByTag/{userId}/{bookTag}/{page}/{pageSize}")//通过书籍标签查找书名,参数为借书者的ID,标签,页号和页面大小
    public R<Page> getBookByTag(@PathVariable Long userId, @PathVariable Integer bookTag, @PathVariable int page,@PathVariable int pageSize) {

        List<String> isbnList = bookTagService.getIsbnByTag(bookTag);
        Page<Book> pageBuilder = new Page<>(page, pageSize);
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Book::getBookIsbn, isbnList);
        bookService.page(pageBuilder, queryWrapper);

        return R.success(getBookDtoByPage(userId, page, pageBuilder));
    }

    @GetMapping("/listByTag/{userId}/{bookTag}/{page}/{pageSize}")//通过书籍标签查找书名,参数为借书者的ID,标签和页号
    public R<Page> getListByTag(@PathVariable Long userId, @PathVariable Integer bookTag, @PathVariable int page, @PathVariable int pageSize) {
        List<BookDto> dtoList = bookService.getDtoListByPage(bookTag, page, pageSize);
        int total = bookService.countDtoList(bookTag);
        User user = userService.getById(userId);
        if (user != null) {
            Double longtitude = user.getUserLongitude(); //获取用户经度
            Double latitude = user.getUserLatitude(); //获取用户纬度
            if (longtitude != null && latitude != null)//经度和纬度都存在时候才能排序。
            {
                dtoList.sort((o1, o2) -> {
                    Double dist1 = Math.sqrt(Math.pow(o1.getUserLongitude() - longtitude, 2) + Math.pow(o1.getUserLatitude() - latitude, 2));
                    Double dist2 = Math.sqrt(Math.pow(o2.getUserLongitude() - longtitude, 2) + Math.pow(o2.getUserLatitude() - latitude, 2));
                    return dist1.compareTo(dist2);
                });
            }
        }

        Page<BookDto> dtoPage = new Page<>(page, pageSize);
        dtoPage.setRecords(dtoList);
        dtoPage.setTotal(total);
        dtoPage.setCurrent(page);
        dtoPage.setSize(pageSize);
        dtoPage.setPages((total + pageSize - 1) / pageSize);

        return R.success(dtoPage);
    }
    
    @GetMapping("/listById/{bookId}")//通过书籍ID查询书籍信息
    public R<Book> getBookById(@PathVariable Long bookId)
    {
    	LambdaQueryWrapper<Book>wrapper = new LambdaQueryWrapper<>();
    	wrapper.eq(Book::getBookId, bookId);
    	
    	Book book = bookService.getOne(wrapper);
    	return book == null? R.error("该书籍不存在") : R.success(book);
    }
    
    @GetMapping("/listRandom/{userId}/{page}/{pageSize}")//随机查询书籍，根据用户距离来推荐书籍
    public R<Page> getListRandom(@PathVariable Long userId, @PathVariable int page, @PathVariable int pageSize)
    {
        Page<Book> pageBuilder = new Page<>(page, pageSize);
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        bookService.page(pageBuilder, queryWrapper);

        return R.success(getBookDtoByPage(userId, page, pageBuilder));
    }

}
