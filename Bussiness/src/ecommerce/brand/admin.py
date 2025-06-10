from django.contrib import admin
from .models import Brand


@admin.register(Brand)
class BrandAdmin(admin.ModelAdmin):
    list_display = ('name', 'slug', 'featured', 'created_at')
    list_filter = ('featured', 'created_at')
    search_fields = ('name', 'description')
    prepopulated_fields = {'slug': ('name',)}
    readonly_fields = ('created_at', 'updated_at')

    fieldsets = (
        (None, {'fields': ('name', 'slug', 'featured')}),
        ('Media', {'fields': ('logo',)}),
        ('Content', {'fields': ('description', 'website')}),
        ('Metadata', {'fields': ('created_at', 'updated_at')}),
    )